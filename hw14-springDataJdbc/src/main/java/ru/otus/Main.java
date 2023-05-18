package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Collections;
@SpringBootApplication
public class Main {



    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setDefaultProperties(Collections.singletonMap("spring.datasource.initialization-mode", "always"));
        app.run(args);




    }
//public static void main(String[] args) {
//    SpringApplication.run(Main.class, args);
//
//}
}





