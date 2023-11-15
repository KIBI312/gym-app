package com.seitov.gym.service.impl;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.seitov.gym.dto.TrainingReportDto;
import com.seitov.gym.entity.Training;
import com.seitov.gym.service.ReportService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EurekaClient eurekaClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MapperFacade orikaMapper;

    @Override
    public void reportTraining(Training training, TrainingReportDto.ActionType actionType) {
        TrainingReportDto dto = orikaMapper.map(training, TrainingReportDto.class);
        dto.setActionType(actionType);
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("gym-workload", false);
        String hostname = instanceInfo.getHostName();
        int port = instanceInfo.getPort();
        StringBuilder url = new StringBuilder("http://")
                .append(hostname)
                .append(":")
                .append(port)
                .append("/api/report");
        restTemplate.postForObject(url.toString(), dto, TrainingReportDto.class);
    }

}