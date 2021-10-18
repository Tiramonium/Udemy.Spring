package com.udemy.spring.components;

import java.text.ParseException;
import com.udemy.spring.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("'${spring.datasource.name}'.equals('devdb') and '${spring.jpa.hibernate.ddl-auto}'.equals('create')")
public class DevComponent implements CommandLineRunner {
    @Autowired
    private DBService dbService;

    @Bean
    public boolean IniciarBancoDesenvolvimento() {
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
    public void run(String... args) throws Exception {
        this.IniciarBancoDesenvolvimento();
    }
}
