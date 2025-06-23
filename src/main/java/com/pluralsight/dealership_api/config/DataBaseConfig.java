package com.pluralsight.dealership_api.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    // We will build the BasicDataSource and store it here.
    private BasicDataSource basicDataSource;

    // This method defines the DataSource bean.
    // Spring will call this and register the DataSource in the ApplicationContext.
    @Bean
    public DataSource dataSource() {
        return basicDataSource;
    }

    // Constructor — Spring will call this and inject the datasource.url property here.
    public DataBaseConfig(@Value("${datasource.url}") String url) {

        // Read username and password from system properties — these were passed as command-line args.
        String username = System.getProperty("dbUsername");
        String password = System.getProperty("dbPassword");

        // Build the BasicDataSource.
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

    }
}

