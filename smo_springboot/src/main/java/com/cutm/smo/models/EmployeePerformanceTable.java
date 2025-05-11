package com.cutm.smo.models;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "employeeperformance")
public class EmployeePerformanceTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int jobid;

    
    private int employeeid;

    
    private int machineid;

    private ZonedDateTime inscan;
    private ZonedDateTime outscan;
    private String style;
    private String jobtype;

    @ManyToOne
    @JoinColumn(name = "style", referencedColumnName = "style", insertable = false, updatable = false)
    private OrdersTable order;

    @OneToOne
    @JoinColumn(name = "workstationjob_jobid", referencedColumnName = "jobid")
    private WorkstationJobsTable workstationjob;
}