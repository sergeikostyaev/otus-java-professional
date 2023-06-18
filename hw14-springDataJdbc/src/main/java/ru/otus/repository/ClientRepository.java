package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client,Long> {
    List<Client> findAll();

    Client save(Client client);

    Optional<Client> findById(long id);
}
