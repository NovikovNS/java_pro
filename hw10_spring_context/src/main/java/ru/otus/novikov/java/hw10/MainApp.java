package ru.otus.novikov.java.hw10;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.novikov.java.hw10.services.StoreService;

@ComponentScan
public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainApp.class);
        StoreService store = context.getBean(StoreService.class);
        store.run();
    }
}
