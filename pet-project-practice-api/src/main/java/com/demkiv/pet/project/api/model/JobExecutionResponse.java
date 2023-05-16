package com.demkiv.pet.project.api.model;


import lombok.Data;

import java.time.Instant;

@Data
public class JobExecutionResponse {
    private String jobId;

    private String name;

    private String type;

    private String subType;

    private String spec;

    private String status;

    private String user;

    private Instant queuedTime;

    private Instant startTime;

    private Instant endTime;

    private String stackTrace;

    private String log;
}
