package com.bank.service;

import com.bank.exception.InsufficientBalanceException;
import com.bank.model.Account;
import com.bank.model.Customer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BankService {

    private final Map<Integer, Customer> customers = new HashMap<>();

    // ---------------- CUSTOMER MANAGEMENT ----------------

    public void registerCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (customers.containsKey(customer.getCustomerId())) {
            throw new IllegalArgumentException(
                    "Customer with ID " + customer.getCustomerId() + " already exists"
            );
        }

        customers.put(customer.getCustomerId(), customer);
    }

    public Customer getCustomer(int customerId) {
        Customer customer = customers.get(customerId);

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer with ID " + customerId + " not found"
            );
        }

        return customer;
    }

    // ---------------- ACCOUNT MANAGEMENT ----------------

    public void addAccountToCustomer(int customerId, Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        Customer customer = getCustomer(customerId);

        // Prevent duplicate account numbers across the bank
        if (findAccount(account.getAccountNumber()) != null) {
            throw new IllegalArgumentException(
                    "Account number " + account.getAccountNumber() + " already exists"
            );
        }

        customer.addAccount(account);
    }

    public Account findAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }

        for (Customer customer : customers.values()) {
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }
            }
        }

        return null;
    }

    // ---------------- TRANSFER ----------------

    public void transfer(String sourceAccountNumber,
                         String targetAccountNumber,
                         BigDecimal amount)
            throws InsufficientBalanceException {

        Account source = findAccount(sourceAccountNumber);
        Account target = findAccount(targetAccountNumber);

        if (source == null) {
            throw new IllegalArgumentException(
                    "Source account " + sourceAccountNumber + " not found"
            );
        }

        if (target == null) {
            throw new IllegalArgumentException(
                    "Target account " + targetAccountNumber + " not found"
            );
        }

        transfer(source, target, amount);
    }

    public void transfer(Account source,
                         Account target,
                         BigDecimal amount)
            throws InsufficientBalanceException {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Accounts cannot be null");
        }

        if (source == target) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid transfer amount");
        }

        source.sendTransfer(amount, target.getAccountNumber());
        target.receiveTransfer(amount, source.getAccountNumber());
    }
}