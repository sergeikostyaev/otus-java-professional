package ru.otus;


import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private static TreeMap<Customer, String> treeMap = new TreeMap<>((c1, c2) -> (int) (c1.getScores() - c2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer,String> temp = treeMap.firstEntry();
        if(temp == null) return null;
        return new AbstractMap.SimpleImmutableEntry<>(new Customer(temp.getKey().getId(), temp.getKey().getName(), temp.getKey().getScores()), temp.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
            Map.Entry<Customer,String> temp = treeMap.higherEntry(customer);
            if(temp == null) return null;
            return new AbstractMap.SimpleImmutableEntry<>(new Customer(temp.getKey().getId(), temp.getKey().getName(), temp.getKey().getScores()), temp.getValue());
    }

    public void add(Customer customer, String data) {
        treeMap.put(customer, data);
    }
}
