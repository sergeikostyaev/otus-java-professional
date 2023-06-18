package ru.otus.model;


import jakarta.annotation.Nonnull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Phone {
    @Id
    private Long id;
    @Nonnull
    private String number;

    private Long client_id;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

}
