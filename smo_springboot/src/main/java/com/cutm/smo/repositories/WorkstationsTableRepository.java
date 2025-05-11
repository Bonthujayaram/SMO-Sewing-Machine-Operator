package com.cutm.smo.repositories;

import com.cutm.smo.models.WorkstationsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WorkstationsTableRepository extends JpaRepository<WorkstationsTable, Long> {
	Optional<WorkstationsTable> findByQrid(String machineid);
}