package org.marlisson.restwithspringboot.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

// AbstractIntegrationTest.Initilizer classe estática responsável por criar o container do mysql dinamicamente
@ContextConfiguration(initializers = AbstractIntegrationTest.Initilizer.class)
public class AbstractIntegrationTest {
    // tem que ser estático porque só pode ter um container do testcontainers durante a execução
    static class Initilizer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0");
                // Startables.deepStart(Stream.of(mysql)).join() intera sobre os parâmetros abaixo
                // Como nenhum abaixo foi especificado em mysql acima, a iteração ocorre sobre os parametros default.
                // .withUsername()
                // .withPassword()
                // .withDatabaseName()


        // static porque só tem que ter uma instância enquanto o contexto estiver inicializado
        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", mysql.getJdbcUrl(),
                    "spring.datasource.username", mysql.getUsername(),
                    "spring.datasource.password", mysql.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            // obter as variáveis de ambiente do application context
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testcontainers = new MapPropertySource("testcontainers",
                    (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testcontainers);
        }
    }
}
