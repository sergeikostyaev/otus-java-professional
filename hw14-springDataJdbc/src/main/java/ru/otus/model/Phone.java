package ru.otus.model;


import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "phone")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Phone {
    private Long id;
    private String number;
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public void setClient(Client client) {
        this.client = client;
        client.getPhones().add(this);
    }
}
