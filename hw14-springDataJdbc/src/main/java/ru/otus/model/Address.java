package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("address")
public class Address {
    @Id
    private Long id;
    @Nonnull
    private String street;

    private Long clientId;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

}
