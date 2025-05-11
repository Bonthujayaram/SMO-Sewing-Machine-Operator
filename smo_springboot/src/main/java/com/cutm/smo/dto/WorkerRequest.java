package com.cutm.smo.dto;

public class WorkerRequest {
    private String employeename;
    private String machineqr;
    private String jobqr;

    public WorkerRequest() {}

    public WorkerRequest(String employeename, String machineqr, String jobqr) {
        this.employeename = employeename;
        this.machineqr = machineqr;
        this.jobqr = jobqr;
    }

    public String getEmployeename() { return employeename; }
    public void setEmployeename(String employeename) { this.employeename = employeename; }
    public String getMachineqr() { return machineqr; }
    public void setMachineid(String machineqr) { this.machineqr = machineqr; }
    public String getJobqr() { return jobqr; }
    public void setJobqr(String jobqr) { this.jobqr = jobqr; }
}