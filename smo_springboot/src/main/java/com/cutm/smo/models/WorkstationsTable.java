package com.cutm.smo.models;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "workstations")
public class WorkstationsTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private int machineid;
    
    private String qrid;

    private String machinename;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status { active, idle, undermaintenance }
}