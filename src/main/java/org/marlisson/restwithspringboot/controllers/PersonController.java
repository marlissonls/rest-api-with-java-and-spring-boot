package org.marlisson.restwithspringboot.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.marlisson.restwithspringboot.controllers.docs.PersonControllerDocs;
import org.marlisson.restwithspringboot.data.dto.PersonDTO;
import org.marlisson.restwithspringboot.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Manging People")
public class PersonController implements PersonControllerDocs {

    @Autowired
    public PersonServices service;
    // public PersonServices service = new PersonServices();

    // Se necessário usar configuração de cors por endpoint, mas manter o controle global em application.yaml
    //@CrossOrigin(origins = "http://localhost:8080")
    //@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8080"})
    //@CrossOrigin(originPatterns = )
    //@RequestMapping(method = RequestMethod.GET,
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public PersonDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO create(@RequestBody PersonDTO person) {

        return service.create(person);
    }

    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO update(@RequestBody PersonDTO person) {

        return service.update(person);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // Opcional. Envia 204
    }
}
