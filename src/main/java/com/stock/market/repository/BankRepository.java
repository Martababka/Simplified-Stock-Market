package com.stock.market.repository;

import com.stock.market.model.BankStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<BankStock, String> {

    Optional<BankStock> findByName(String name);
}
