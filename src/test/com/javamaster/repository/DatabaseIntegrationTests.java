package com.javamaster.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("scripts/RESET_AUTO_INCREMENT.sql")
public abstract class DatabaseIntegrationTests {

    private static final PostgreSQLContainer database;

    static {
        database = new PostgreSQLContainer("postgres")
                .withDatabaseName("postgres")
                .withPassword("postgres")
                .withUsername("postgres");
        database.start();
    }

    @DynamicPropertySource
    private static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected DataSource dataSource;

    protected void saveAndFlush(final Object... entities) {
        for (var entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.flush();
        entityManager.clear();
    }
}