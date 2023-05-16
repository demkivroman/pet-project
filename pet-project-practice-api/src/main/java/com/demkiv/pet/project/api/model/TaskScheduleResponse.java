package com.demkiv.pet.project.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskScheduleResponse {
    private String name;
    private boolean enabled;
    private int runCount;
    private float averageRuntime;
    private String config;
    private JobExecutionResponse jobExecutionResponse;
}
