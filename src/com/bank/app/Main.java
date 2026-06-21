package com.bank.app;

import com.bank.model.SavingsAccount;
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) throws Exception {

        SavingsAccount savings =
                new SavingsAccount(
                        "SA001",
                        new BigDecimal("10000"),
                        new BigDecimal("1000")
                );

        System.out.println("Initial Balance:");
        System.out.println(savings.getBalance());

        savings.deposit(new BigDecimal("5000"));

        System.out.println("After Deposit:");
        System.out.println(savings.getBalance());

        savings.withdraw(new BigDecimal("2000"));

        System.out.println("After Withdrawal:");
        System.out.println(savings.getBalance());
    }
}