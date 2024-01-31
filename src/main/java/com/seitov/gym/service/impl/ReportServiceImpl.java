package com.seitov.gym.service.impl;

import com.seitov.gym.dto.TrainingReportDto;
import com.seitov.gym.entity.Training;
import com.seitov.gym.service.ReportService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportServiceImpl implements ReportService {

//    @Autowired
//    private EurekaClient eurekaClient;
    @Value("${service.report.host}")
    private String reportHost;
    @Value("${service.report.port}")
    private int reportPort;
    @Value("${amazon.sqs.queue.name}")
    private String queue;
    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    private Queue queue;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private MapperFacade orikaMapper;

    @Override
    public void reportTraining(Training training, TrainingReportDto.ActionType actionType) {
        TrainingReportDto dto = orikaMapper.map(training, TrainingReportDto.class);
        dto.setActionType(actionType);
//        sendViaRest(dto);
        sendViaJMS(dto);
    }

    public void sendViaRest(TrainingReportDto dto) {
        StringBuilder url = new StringBuilder("http://")
                .append(reportHost)
                .append(":")
                .append(reportPort)
                .append("/api/report/record");
        restTemplate.postForObject(url.toString(), dto, TrainingReportDto.class);
    }

    private void sendViaJMS(TrainingReportDto dto) {
        jmsTemplate.convertAndSend(queue, dto);
    }

}
