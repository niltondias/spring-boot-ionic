package br.com.finework.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.finework.cursomc.services.DBService;
import br.com.finework.cursomc.services.EmailService;
import br.com.finework.cursomc.services.MocEmailService;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;        
    }

    @Bean
    public  EmailService emailService() {
        return new MocEmailService();
    }
}