package com.cutm.smo.repositories;

import com.cutm.smo.models.EmployeePerformanceTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeePerformanceTableRepository extends JpaRepository<EmployeePerformanceTable, Long> {
    Optional<EmployeePerformanceTable> findTopByMachineidAndJobidAndOutscanIsNull(int machine, int jobid);
    List<EmployeePerformanceTable> findByEmployeeid(int employeeid);
    List<EmployeePerformanceTable> findAll();
}