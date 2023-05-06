package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.server.WebServer;
import ru.otus.server.WebServerSimple;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        WebServer usersWebServer = new WebServerSimple(WEB_SERVER_PORT, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}