package ru.otus.service;

import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public interface DbClientService {

    Client saveClient(Client client);

    Optional<Client> getClientById(long id);

    List<Client> findAll();

}
