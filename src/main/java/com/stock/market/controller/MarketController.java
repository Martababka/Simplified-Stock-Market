package com.stock.market.controller;

import com.stock.market.service.MarketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarketController {

    MarketService marketService;

    @PostMapping("/wallets/{walletId}/stocks/{stockName}")
    public void openTrade(@PathVariable String walletId,
                          @PathVariable String stockName,
                          @RequestBody Map<String, String> body) {
        marketService.openTrade(walletId, stockName, body.get("type"));
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<Map<String, Object>> getWallet(@PathVariable String walletId) {
        return ResponseEntity.ok().body(Map.of("id", walletId, "stocks", marketService.getWallet(walletId)));
    }

    @GetMapping("/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<Integer> getWalletStock(@PathVariable String walletId, @PathVariable String stockName) {
        return ResponseEntity.ok().body(marketService.getWalletStock(walletId, stockName));
    }

    @GetMapping("/stocks")
    public ResponseEntity<Map<String, Object>> getStocks() {
        return ResponseEntity.ok().body(Map.of("stocks", marketService.getStocks()));

    }

    @PostMapping("/stocks")
    public void setStocks(@RequestBody Map<String, List<Map<String, Object>>> body) {
        marketService.setBankState(body.get("stocks"));
    }

    @GetMapping("/log")
    public ResponseEntity<Map<String, Object>> getLog() {
        return ResponseEntity.ok().body(marketService.getLogs());
    }

    @PostMapping("/exit")
    public void exit() {
        System.exit(1);
    }
}
