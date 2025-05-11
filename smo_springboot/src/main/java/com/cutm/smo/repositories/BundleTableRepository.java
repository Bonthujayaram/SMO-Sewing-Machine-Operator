package com.cutm.smo.repositories;

import com.cutm.smo.models.BundleTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BundleTableRepository extends JpaRepository<BundleTable, Long> {
    Optional<BundleTable> findByJobid(int jobid);
    Optional<BundleTable> findByQrid(String qrid);
}