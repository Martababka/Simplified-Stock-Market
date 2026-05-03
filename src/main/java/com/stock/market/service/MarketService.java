package com.stock.market.service;

import com.stock.market.model.Audit;
import com.stock.market.model.BankStock;
import com.stock.market.model.Wallet;
import com.stock.market.repository.AuditRepository;
import com.stock.market.repository.BankRepository;
import com.stock.market.repository.WalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarketService {

    BankRepository bankRepo;
    WalletRepository walletRepo;
    AuditRepository logRepo;

    public List<Map<String, Object>> getWallet(String walletId) {
        List<Wallet> stocks = walletRepo.findAllByWalletId(walletId);
        return stocks.stream()
                .filter(wallet -> wallet.getQuantity() > 0)
                .map(wallet -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", wallet.getStockName());
                    map.put("quantity", wallet.getQuantity());
                    return map;
                }).toList();
    }

    public int getWalletStock(String walletId, String stockName) {
        return walletRepo.findByWalletIdAndStockName(walletId, stockName)
                .map(Wallet::getQuantity)
                .orElseThrow();
    }

    public List<Map<String, Object>> getStocks() {
        List<Map<String, Object>> stocks = new ArrayList<>();
        bankRepo.findAll().forEach(stock -> stocks.add(Map.of("name", stock.getName(), "quantity", stock.getQuantity())));
        return stocks;
    }

    public Map<String, Object> getLogs() {
        return Map.of("log", logRepo.findAllByOrderByTimestampAsc().stream().map(log ->
                Map.of("type", log.getType(), "wallet_id", log.getWalletId(), "stock_name", log.getStockName())
        ).toList());
    }

    @Transactional
    public void openTrade(String walletId, String stockName, String type) {
        BankStock bankStock = bankRepo.findByName(stockName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found in bank"));

        Wallet walletStock = walletRepo.findByWalletIdAndStockName(walletId, stockName)
                .orElse(new Wallet(null, walletId, stockName, 0));

        if ("buy".equalsIgnoreCase(type)) {
            if (bankStock.getQuantity() < 1)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stock in bank");
            bankStock.setQuantity(bankStock.getQuantity() - 1);
            walletStock.setQuantity(walletStock.getQuantity() + 1);
        } else if ("sell".equalsIgnoreCase(type)) {
            if (walletStock.getQuantity() < 1)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stock in wallet");
            walletStock.setQuantity(walletStock.getQuantity() - 1);
            bankStock.setQuantity(bankStock.getQuantity() + 1);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid type");
        }

        bankRepo.save(bankStock);
        walletRepo.save(walletStock);
        logRepo.save(new Audit(null, type, walletId, stockName, LocalDateTime.now()));
    }

    @Transactional
    public void setBankState(List<Map<String, Object>> stocksInput) {
        bankRepo.deleteAll();
        for (Map<String, Object> s : stocksInput) {
            bankRepo.save(new BankStock((String) s.get("name"), (Integer) s.get("quantity")));
        }
    }
}
