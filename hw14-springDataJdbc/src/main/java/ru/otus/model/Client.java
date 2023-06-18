package ru.otus.model;

import lombok.*;
import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceCreator;


import java.util.Set;

@Getter
@Setter
@Table(name = "client")
@ToString
@NoArgsConstructor
public class Client {

    @Id
    private Long id;
    @Nonnull
    private String name;

    @MappedCollection(idColumn = "client_id")
    private Address address;

    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones;


    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }


    public Client(Long id, String name, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.phones = phones;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address.getStreet() +
                ", address id=" + address.getId() +
                ", address client_id=" + address.getClientId() +
                ", phones=" + phones +
                '}';
    }
}

