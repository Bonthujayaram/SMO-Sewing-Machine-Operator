package com.cutm.smo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employeepermission")
public class EmployeePermissionTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    // Assuming a relationship with EmployeeInfoTable (adjust based on your schema)
    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "employeeid", referencedColumnName = "employeeid")
    })
    private EmployeeInfoTable employee;

    
    public int getEmployeeid() {
        return employee.getEmployeeid();
    }
}