package com.cutm.smo.repositories;

import com.cutm.smo.models.WorkstationJobsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WorkstationJobsTableRepository extends JpaRepository<WorkstationJobsTable, Long> {
    Optional<WorkstationJobsTable> findTopByMachineidAndJobidAndOutscanIsNull(int machineid, int jobid);
}