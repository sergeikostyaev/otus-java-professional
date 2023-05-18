package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Service;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DbClientServiceImpl implements DbClientService {
    private static final Logger log = LoggerFactory.getLogger(DbClientServiceImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public DbClientServiceImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            log.info("\u001B[31m" + "savedClient:{}" + "\u001B[0m", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClientById(long id) {
        return transactionManager.doInTransaction(() -> {
        var clientOptional = clientRepository.findById(id);
        log.info("\u001B[31m" + "got client: {}" + "\u001B[0m", clientOptional);
        return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(() -> {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("\u001B[31m" + "clientList:{}" + "\u001B[0m", clientList);
        return clientList;
        });
    }
}

