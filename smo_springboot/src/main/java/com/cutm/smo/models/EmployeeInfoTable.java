package com.cutm.smo.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "employeeinfo")
public class EmployeeInfoTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private int employeeid;

    private String loginid;

    private String name;

    private String contact;

    private String email;

    private String district;

    private String ro;

    private String branch;

    private String aadhar;

    private String pancard;

    private String validfrom;

    private String validto;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Accommodation accommodation;

    public enum Sex { male, female, other }
    public enum Accommodation { yes, no }
}