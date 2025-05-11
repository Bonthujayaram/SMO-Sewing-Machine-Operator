package com.cutm.smo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cutm.smo.models.EmployeeInfoTable;
@Repository
public interface EmployeeInfoTableRepository extends JpaRepository<EmployeeInfoTable, Long> {
	EmployeeInfoTable findByEmployeeid(Integer employeeid);
}
