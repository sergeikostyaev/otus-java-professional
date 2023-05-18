package ru.otus.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "client")
public class Client implements Cloneable {
    private Long id;
    private String name;
    private Address address;
    private List<Phone> phones;



    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = new ArrayList<>();
        phones.stream().forEach(p -> {
            p.setClient(this);
        });
    }

    @Override
    public Client clone() {

        Address copyAdress = new Address(address.getId(), address.getStreet());

        List<Phone> clonedPhones = new ArrayList<>();
        phones.stream().forEach(p ->{
            clonedPhones.add(new Phone(p.getId(), p.getNumber()));
        });

        return new Client(this.id, this.name, copyAdress, clonedPhones);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}

