package com.stock.market.repository;

import com.stock.market.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByWalletIdAndStockName(String walletId, String stockName);

    List<Wallet> findAllByWalletId(String walletId);
}
