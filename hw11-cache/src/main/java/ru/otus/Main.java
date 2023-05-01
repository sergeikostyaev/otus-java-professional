package ru.otus;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.HwCache;
import ru.otus.cache.MyCache;
import ru.otus.hibernate.core.repository.DataTemplateHibernate;
import ru.otus.hibernate.core.repository.HibernateUtils;
import ru.otus.hibernate.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.hibernate.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.hibernate.crm.model.Address;
import ru.otus.hibernate.crm.model.Client;
import ru.otus.hibernate.crm.model.Phone;
import ru.otus.hibernate.crm.service.DbServiceClientImpl;

import java.util.List;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {


        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Phone.class, Address.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var cache = new MyCache<String, Client>();
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate,cache);

//        var client = new Client(null, "Petir", new Address(null, "AnyStreet"), List.of(new Phone(null, "13-555-22"),
//                new Phone(null, "14-666-333")));

//        dbServiceClient.saveClient(client);
//        var loadedSavedClient = dbServiceClient.getClient(1);
//        System.out.println(loadedSavedClient.get().getAddress().getStreet());
        dbServiceClient.setCacheStatus(false);
        var limit = 150;
        for (var idx = 1; idx < limit; idx++) {
            var client = new Client(Long.valueOf(idx), "Petir"+idx, new Address(null, "AnyStreet"+idx), List.of(new Phone(null, "13-555-22"+idx),
                    new Phone(null, "14-666-333"+idx)));
            dbServiceClient.saveClient(client);
        }

        long startOff = System.currentTimeMillis();
        for (var idx = 1; idx < limit; idx++) {
            System.out.println(dbServiceClient.getClient(idx));
        }
        long finishOff = System.currentTimeMillis();



        dbServiceClient.setCacheStatus(true);
        limit *= 2;
        for (var idx = 1; idx < limit; idx++) {

            var client = new Client(Long.valueOf(idx), "Petir"+idx, new Address(null, "AnyStreet"+idx), List.of(new Phone(null, "13-555-22"+idx),
                    new Phone(null, "14-666-333"+idx)));
            dbServiceClient.saveClient(client);
        }

        long startOn = System.currentTimeMillis();
        for (var idx = 1; idx < limit; idx++) {

            System.out.println(dbServiceClient.getClient(idx));
        }
        long finishOn = System.currentTimeMillis();

        System.out.println("Cache off : " + (finishOff-startOff) + "\nCache on : " + (finishOn-startOn));
    }
}
