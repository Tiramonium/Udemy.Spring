package com.udemy.spring.components;

import java.text.ParseException;
import com.udemy.spring.services.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "spring.h2.console.enabled", havingValue = "true", matchIfMissing = false)
public class LocalComponent implements CommandLineRunner {
    @Autowired
    private LocalService localService;

    @Bean
    public boolean IniciarBancoLocal() {
        try
        {
            localService.IniciarBanco();
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