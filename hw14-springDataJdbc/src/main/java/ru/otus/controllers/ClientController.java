package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.service.DbClientService;

import java.util.List;
import java.util.Set;

@Controller
public class ClientController {
    @Autowired
    DbClientService clientService;

    @GetMapping({"/","/clients"})
    public String clientsListView(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients",clients);
        return "clients";
    }

}
