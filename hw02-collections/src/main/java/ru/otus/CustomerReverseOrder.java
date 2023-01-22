package ru.otus;


import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {

    private final Deque<Customer> linkedList = new LinkedList<>();

    public void add(Customer customer) {
        linkedList.addFirst(customer);
    }

    public Customer take() {
        return linkedList.pollFirst();
    }
}
