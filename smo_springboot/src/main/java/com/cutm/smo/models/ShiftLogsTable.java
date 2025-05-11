package com.cutm.smo.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "shiftlogs")
public class ShiftLogsTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ZonedDateTime shiftstarttime;

    private ZonedDateTime shiftendtime;

    private int shiftduration;

    @OneToOne
    @JoinColumn(name = "employeeid", referencedColumnName = "employeeid")
    private EmployeeInfoTable employee;
    
    public int getEmployeeid() {
        return employee.getEmployeeid();
    }
}