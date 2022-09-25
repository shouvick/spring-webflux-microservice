package com.shouvick.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Service
public class DataLoader implements CommandLineRunner {

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(schemaSql.getInputStream(), StandardCharsets.UTF_8);
        entityTemplate
                .getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();
    }
}
