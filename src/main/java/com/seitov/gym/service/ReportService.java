package com.seitov.gym.service;

import com.seitov.gym.dto.TrainingReportDto;
import com.seitov.gym.entity.Training;

public interface ReportService {

    void reportTraining(Training training, TrainingReportDto.ActionType actionType);

}