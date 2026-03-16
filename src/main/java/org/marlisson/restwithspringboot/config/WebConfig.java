// configuração para a dependência 'com.fasterxml.jackson.dataformat' em função do Content Negotiation

package org.marlisson.restwithspringboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
//import org.springframework.lang.NonNull;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originPatterns:default}") // default => site origem
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                //.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override                               //@NonNull ContentNegotiationConfigurer configurer
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Esse código inicial foi apagado: WebMvcConfigurer.super.configureContentNegotiation(configurer);

        // Via EXTENSION como http://localhost:8080/api/person/v1/1.json ou .xml está depreciado
        // Via QUERY PARAM http://localhost:8080/api/person/v1/1?mediaType=xml ou json. Funciona.
        // .ignoreAcceptHeader(true) fará ignorar o valor da key Accept que estiver o Headers de uma requisição como "Accept": "application/json" ou outros.
        // .useRegisteredExtensionsOnly(false) permite customizar os tipos de formato de resposta que a api poderá servir.
        /*configurer.favorParameter(true)
            .parameterName("mediaType")
            .ignoreAcceptHeader(true)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
        */

        // Via HEADER PARAM http://localhost:8080/api/person/v1/1
        configurer.favorParameter(false)
            .ignoreAcceptHeader(false) // Se mudar para false, XML terá mais prioridade sobre JSON. Se manter true, o Accept Head não será ignorado e XML será o padrão
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);
    }
}
