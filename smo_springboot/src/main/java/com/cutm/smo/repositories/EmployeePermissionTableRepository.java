package com.cutm.smo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cutm.smo.models.EmployeePermissionTable;
@Repository
public interface EmployeePermissionTableRepository extends JpaRepository<EmployeePermissionTable, Long> {

}
