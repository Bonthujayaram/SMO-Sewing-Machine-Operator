package com.cutm.smo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class RolesTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private int roleid;

    private String rolename;

    private boolean qrscanning;

    private boolean assignqr;

    private boolean unassignqr;

    private boolean viewindividualdashboard;

    private boolean viewpersonaldashboard;

    private boolean mergeqr;

    private boolean viewoveralldashboard;
}