package com.udemy.spring.components;

import java.text.ParseException;
import com.udemy.spring.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "spring.datasource.name", havingValue = "localdb", matchIfMissing = false)
public class LocalComponent implements CommandLineRunner {
    @Autowired
    private DBService dbService;

    @Bean
    public boolean IniciarBancoLocal() {
        try
        {
            dbService.IniciarBanco();
            return true;
        }
        catch (ParseException exception)
        {
            return false;
        }
    }

    @Override
    public void run(String... args) {
        this.IniciarBancoLocal();
    }
}