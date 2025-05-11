package com.cutm.smo.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "machinemaintenance")
public class MachineMaintenanceTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private Long maintenanceid;

    @ManyToOne
    @JoinColumn(name = "machineid", referencedColumnName = "machineid")
    private WorkstationsTable machine;

    private ZonedDateTime servicedate;

    private ZonedDateTime nextmaintenancedue;

    @Column(columnDefinition = "TEXT")
    private String maintenancedetails;
}