package com.cutm.smo.repositories;

import com.cutm.smo.models.EmployeeLoginTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeLoginTableRepository extends JpaRepository<EmployeeLoginTable, Long> {
    EmployeeLoginTable findByLoginid(String loginid);
    Optional<EmployeeLoginTable> findByEmployeeinfo_Name(String employeename);
    
    @Query("SELECT el.employeeinfo.name FROM EmployeeLoginTable el WHERE el.role.roleid = 2")
    List<String> findWorkerNames();
    

    
}