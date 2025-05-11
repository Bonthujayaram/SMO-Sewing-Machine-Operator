package com.cutm.smo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.cutm.smo.models.InvoicesTable;
@Repository
public interface InvoicesTableRepository extends JpaRepository<InvoicesTable, Long> {

}
