package com.seitov.gym.controller;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.seitov.gym.dto.TraineeDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.dto.common.FullName;
import com.seitov.gym.dto.common.PersonalInfo;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.User;
import com.seitov.gym.service.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TraineeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class TraineeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TraineeService traineeService;

    private final Trainee trainee = new Trainee();
    private final TraineeDto traineeDto = new TraineeDto();

    @BeforeEach
    public void initData() {
        //Trainee init
        User user = new User();
        user.setUsername("John.Smith");
        user.setFirstName("John");
        user.setLastName("Smith");
        trainee.setUser(user);
        trainee.setAddress("NY Street");
        trainee.setDateOfBirth(LocalDate.of(1975, 3, 15));
        //TraineeDto init
        FullName fullName = new FullName(trainee.getUser().getFirstName(), trainee.getUser().getLastName());
        PersonalInfo personalInfo = new PersonalInfo(fullName, trainee.getDateOfBirth(),trainee.getAddress());
        traineeDto.setPersonalInfo(personalInfo);
        traineeDto.setIsActive(trainee.getUser().getIsActive());
    }

    @Test
    public void testTraineeCreation() throws Exception {
        //given
        FullName fullName = new FullName("John", "Smith");
        PersonalInfo userDto = new PersonalInfo(fullName,
                LocalDate.of(1975, 3, 15), "NY street");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "John");
        jsonObject.put("lastName", "Smith");
        jsonObject.put("dateOfBirth", "15-03-1975");
        jsonObject.put("address", "NY street");
        UsernamePasswordDto usernamePasswordDto = new UsernamePasswordDto("John.Smith", "pass123456");
        //when
        when(traineeService.createTrainee(userDto)).thenReturn(usernamePasswordDto);
        //then
        MvcResult result = mockMvc.perform(post("/api/trainee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toJSONString()))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("John.Smith"));
        assertTrue(result.getResponse().getContentAsString().contains("pass123456"));
    }

    @Test
    public void testTraineeCreationInvalidDateFormat() throws Exception {
        //given
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "John");
        jsonObject.put("lastName", "Smith");
        jsonObject.put("dateOfBirth", "1975-03-15");
        jsonObject.put("address", "NY street");
        //then
        MvcResult result = mockMvc.perform(post("/api/trainee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Date should be in format: dd-MM-yyyy"));
    }

    @Test
    public void getTraineeProfile() throws Exception {
        //given
        String username = "John.Smith";
        //when
        when(traineeService.getTrainee(username)).thenReturn(traineeDto);
        //then
        MvcResult result = mockMvc.perform(get("/api/trainee/" + username))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("\"firstName\":\"John\""));
        assertTrue(result.getResponse().getContentAsString().contains("\"lastName\":\"Smith\""));
    }

    @Test
    public void getTraineeProfileWithNonExistingUsername() throws Exception {
        //given
        String username = "Vasiliy.Smith";
        //when
        when(traineeService.getTrainee(username)).thenThrow(new UsernameNotFoundException("User with username: Vasiliy.Smith doesn't exist"));
        //then
        MvcResult result = mockMvc.perform(get("/api/trainee/" + username))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("User with username: Vasiliy.Smith doesn't exist"));
    }

}
