package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.otus.model.Client;

public interface ClientRepository extends CrudRepository<Client,Long> {
}
