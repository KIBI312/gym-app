package com.seitov.gym.controller;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.dto.common.FullName;
import com.seitov.gym.entity.TrainingType;
import com.seitov.gym.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TrainerController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    @Test
    public void trainerCreation() throws Exception {
        //given
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFullName(new FullName("John", "Smith"));
        trainerDto.setSpecialization(TrainingType.Name.fitness);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "John");
        jsonObject.put("lastName", "Smith");
        jsonObject.put("specialization", "fitness");
        UsernamePasswordDto usernamePasswordDto = new UsernamePasswordDto("John.Smith", "pass123456");
        //when
        when(trainerService.createTrainer(trainerDto)).thenReturn(usernamePasswordDto);
        //then
        MvcResult result = mockMvc.perform(post("/api/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toJSONString()))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("John.Smith"));
        assertTrue(result.getResponse().getContentAsString().contains("pass123456"));
    }

    @Test
    public void trainerCreationInvalidSpecialization() throws Exception {
        //given
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "John");
        jsonObject.put("lastName", "Smith");
        jsonObject.put("specialization", "asd");
        //then
        MvcResult result = mockMvc.perform(post("/api/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Please select valid specialization value"));
    }

}
