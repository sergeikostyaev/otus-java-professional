package ru.otus.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.hibernate.crm.model.Address;
import ru.otus.hibernate.crm.model.Client;
import ru.otus.hibernate.crm.model.Phone;
import ru.otus.hibernate.crm.service.DBServiceClient;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientsApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final ObjectMapper mapper;

    DBServiceClient dbServiceClient;

    public ClientsApiServlet(ObjectMapper mapper, DBServiceClient dbServiceClient) {
        this.mapper = mapper;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var id = extractIdFromRequest(request);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();

        if(id == -1L){

            out.print(mapper.writeValueAsString(dbServiceClient.findAll()));
        }else {
            out.print(mapper.writeValueAsString(dbServiceClient.getClient(id).get()));
        }
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
