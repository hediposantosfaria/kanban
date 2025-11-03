package br.com.facilit.config;


import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class DatabaseHooks extends CucumberSpringConfiguration {

    @Autowired
    private DataSource dataSource;

    @Before(order = 0)
    public void limparBase() {
        new ResourceDatabasePopulator(new ClassPathResource("/cleanup.sql")).execute(dataSource);
    }
}
