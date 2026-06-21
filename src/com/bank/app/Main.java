package com.bank.app;

import com.bank.model.CurrentAccount;
import com.bank.model.Customer;
import com.bank.model.SavingsAccount;
import com.bank.service.BankService;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) throws Exception {

        BankService bankService = new BankService();

        Customer customer = new Customer(
                1,
                "Dhananjay",
                "Hasthinapuram",
                "1231231234"
        );

        bankService.registerCustomer(customer);

        SavingsAccount savings = new SavingsAccount(
                "SA001",
                new BigDecimal("10000"),
                new BigDecimal("1000")
        );

        CurrentAccount current = new CurrentAccount(
                "CA001",
                new BigDecimal("5000"),
                new BigDecimal("3000")
        );

        bankService.addAccountToCustomer(1, savings);
        bankService.addAccountToCustomer(1, current);

        System.out.println("Customer Details:");
        System.out.println(bankService.getCustomer(1));

        bankService.transfer("SA001", "CA001", new BigDecimal("2000"));

        System.out.println("\nAfter Transfer:");
        System.out.println("Savings Balance: " + savings.getBalance());
        System.out.println("Current Balance: " + current.getBalance());

        System.out.println("\nSavings Transactions:");
        System.out.println(savings.getTransactions());

        System.out.println("\nCurrent Transactions:");
        System.out.println(current.getTransactions());
    }
}