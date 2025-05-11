package com.cutm.smo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cutm.smo.models.ShiftLogsTable;
@Repository
public interface ShiftLogsTableRepository extends JpaRepository<ShiftLogsTable, Long> {

}
