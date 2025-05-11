package com.cutm.smo.models;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "bundle")
public class BundleTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "jobid", unique = true)
    private int jobid;

    private String qrid;
    private String style;
    private String type;
    private String size;
    private int quantity;
    private ZonedDateTime starttime;
    private ZonedDateTime endtime;
    private boolean isfree;
}