package com.cutm.smo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.cutm.smo.models.OrdersTable;
@Repository
public interface OrdersTableRepository extends JpaRepository<OrdersTable, Long> {

}
