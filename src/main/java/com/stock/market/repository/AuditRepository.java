package com.stock.market.repository;

import com.stock.market.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {

    List<Audit> findAllByOrderByTimestampAsc();
}
