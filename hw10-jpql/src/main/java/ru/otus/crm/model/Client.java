package ru.otus.crm.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = {CascadeType.ALL})
    private Address address;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "client")
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
        if(phones == null){
            this.phones = null;
        }else {
            this.phones = new ArrayList<>();
            phones.stream().forEach(p -> p.setClient(this));
        }
    }

    @Override
    public Client clone() {
        Address copyAddress = null;
        List<Phone> clonedPhones = null;
        if (address != null) {
             copyAddress = new Address(address.getId(), address.getStreet());
        }
        if(phones != null) {

            for(Phone p : phones){
                clonedPhones.add(new Phone(p.getId(), p.getNumber()));
            }

        }
        return new Client(this.id, this.name, copyAddress, clonedPhones);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
