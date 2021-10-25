package com.udemy.spring.components;

import java.text.ParseException;
import java.util.Objects;
import com.udemy.spring.services.DBService;
import com.udemy.spring.services.EmailService;
import com.udemy.spring.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "spring.datasource.name", havingValue = "devdb", matchIfMissing = false)
public class DevComponent implements CommandLineRunner {
    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dbAction;

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

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.dbAction != null && (Objects.equals(this.dbAction, "create") || Objects.equals(this.dbAction, "create-drop")))
        {
            this.IniciarBancoDesenvolvimento();
        }
    }
}
