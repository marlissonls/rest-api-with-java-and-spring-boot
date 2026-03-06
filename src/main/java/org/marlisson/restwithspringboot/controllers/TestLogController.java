package org.marlisson.restwithspringboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestLogController {

    private final Logger logger =  LoggerFactory.getLogger(TestLogController.class.getName());
    //Logger do slf4j e não do utils
    @GetMapping("api/test/v1")
    public String testLog() {
        logger.debug("This is an DEBUG log");
        logger.info("This is an INFO log");
        logger.warn("This is an WARN log");
        logger.error("This is an ERROR log");
        return "Logs generated successfully";
        /*
         * O SLF4J usa níveis de log: DEBUG < INFO < WARN < ERROR.
         * O nível configurado define o mínimo que será exibido.
         * Exemplo: se estiver INFO, apenas INFO, WARN e ERROR aparecem.
         * Exemplos 2: se estiver WARN, apenas WARNE e ERROR aparecem.
         */
    }
}
