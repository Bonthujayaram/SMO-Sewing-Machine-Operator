package com.cutm.smo.models;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "employeelogin")
public class EmployeeLoginTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String loginid;
    private String password;
    private boolean status;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "roleid", referencedColumnName = "roleid")
    })
    private RolesTable role;

    private ZonedDateTime createdon;
    private String androidip;
    private String ipaddress;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "employeeid", referencedColumnName = "employeeid")
    })
    private EmployeeInfoTable employeeinfo;

    // Getter for employeeid
    public int getEmployeeid() {
        return employeeinfo.getEmployeeid();
    }
}