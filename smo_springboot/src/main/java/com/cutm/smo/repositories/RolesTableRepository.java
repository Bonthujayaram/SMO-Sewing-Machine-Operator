package com.cutm.smo.repositories;

import com.cutm.smo.models.RolesTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesTableRepository extends JpaRepository<RolesTable, Long> {
    RolesTable findByRoleid(int roleid);
}