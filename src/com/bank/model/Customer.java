package com.bank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {

    private final int customerId;
    private String customerName;
    private String address;
    private String phoneNumber;

    private final List<Account> accounts = new ArrayList<>();

    public Customer(int customerId,
                    String customerName,
                    String address,
                    String phoneNumber) {

        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }

        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // ---------------- GETTERS ----------------

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    // ---------------- SETTERS ----------------

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        this.phoneNumber = phoneNumber;
    }

    // ---------------- ACCOUNT MANAGEMENT ----------------

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.add(account);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}