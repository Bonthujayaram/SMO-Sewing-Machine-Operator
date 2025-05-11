package com.cutm.smo.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class BundleDashboard {
	private int jobid;
    private String machineName;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

}
