package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Client;

import ru.otus.service.DbClientService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClientRestController {
    @Autowired
    DbClientService dbClientService;

    @GetMapping({"/client/list"})
    public String clientsListView(Model model) {
        var clientList = dbClientService.findAll();
        return clientList.toString();
    }

    @GetMapping("/client/{id}")
    public Optional<Client> getClientById(@PathVariable(name = "id") long id) {
        return dbClientService.getClientById(id);
    }

    @GetMapping("/client/random")
    public Optional<Client> getRandomClient() {
        return dbClientService.getRandomClient();
    }

    @PostMapping("/client")
    public Client saveClient(@RequestBody Client client) {
        return dbClientService.saveClient(client);
    }

}
