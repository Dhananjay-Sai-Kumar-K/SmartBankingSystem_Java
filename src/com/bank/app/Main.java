package com.bank.app;

import com.bank.model.Customer;

public class Main {

    public static void main(String[] args) {

        Customer c1 = new Customer(
                1,
                "Dhananjay",
                "Hasthinapuram",
                "1231231234"
        );

        System.out.println(c1);
    }
}