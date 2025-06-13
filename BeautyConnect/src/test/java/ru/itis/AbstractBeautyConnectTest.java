package ru.itis;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.PostgreSQLContainer;


@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(initializers = {AbstractBeautyConnectTest.PostgresqlContainerInitializer.class})
public abstract class AbstractBeautyConnectTest {

    static class PostgresqlContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpassword")
                .withExposedPorts(5432);
        static {
            POSTGRESQL_CONTAINER.start();
        }

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword(),
                    "spring.datasource.driver-class-name=org.postgresql.Driver"
            );
        }
    }
}


