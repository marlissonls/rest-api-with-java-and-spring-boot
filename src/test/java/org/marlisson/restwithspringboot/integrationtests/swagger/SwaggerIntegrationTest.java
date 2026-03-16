package org.marlisson.restwithspringboot.integrationtests.swagger;

import org.junit.jupiter.api.Test;
import org.marlisson.restwithspringboot.configs.TestConfigs;
import org.marlisson.restwithspringboot.integrationtests.testcontainers.AbstractIntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Antes: "@SpringBootTest" apenas
// DEFINED_PORT virá através do atributo server.port: 8888 no application.yaml de testes
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldDisplaySwaggerUIPage() {
        var content = given()
                .basePath("/swagger-ui/index.html")
                    .port(TestConfigs.SERVER_PORT)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        assertTrue(content.contains("Swagger UI"));
    }
}
