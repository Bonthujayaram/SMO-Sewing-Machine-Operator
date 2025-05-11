package com.cutm.smo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.cutm.smo.models.MachineMaintenanceTable;
@Repository
public interface MachineMaintenanceTableRepository extends JpaRepository<MachineMaintenanceTable, Long> {

}
