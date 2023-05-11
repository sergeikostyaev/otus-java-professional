package ru.otus.hibernate.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phone")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Phone {

    @Id
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Column(name = "id")
    private Long id;
    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @JsonBackReference
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
