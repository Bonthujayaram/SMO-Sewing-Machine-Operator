package com.cutm.smo.models;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "workstationjobs")
public class WorkstationJobsTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "employeeid")
    private EmployeeLoginTable employeeid;

    private int machineid;

    @Enumerated(EnumType.STRING)
    private JobStatus jobstatus;

    private ZonedDateTime inscan;
    private ZonedDateTime outscan;

   
    private int jobid;

    

    public enum JobStatus { inprogress, pending, completed }
}