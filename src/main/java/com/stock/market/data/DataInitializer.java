package com.stock.market.data;

import com.stock.market.model.BankStock;
import com.stock.market.model.Wallet;
import com.stock.market.repository.BankRepository;
import com.stock.market.repository.WalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitializer implements CommandLineRunner {

    BankRepository bankRepo;
    WalletRepository walletRepo;


    @Override
    public void run(String... args) {
        if (bankRepo.count() == 0) {
            bankRepo.saveAll(List.of(
                    new BankStock("Google", 1000),
                    new BankStock("Apple", 500),
                    new BankStock("Tesla", 250),
                    new BankStock("Bitcoin", 10)
            ));
            System.out.println(">>> Default stocks created...");
        }

        if (walletRepo.count() == 0) {
            walletRepo.saveAll(List.of(
                    new Wallet(null, "user_1", "Apple", 50),
                    new Wallet(null, "investor_99", "Tesla", 5),
                    new Wallet(null, "investor_99", "Bitcoin", 1)
            ));
            System.out.println(">>> Default wallets created...");
        }
    }
}
