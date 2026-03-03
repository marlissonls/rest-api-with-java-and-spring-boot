package org.marlisson.restwithspringboot.controllers;

import org.marlisson.restwithspringboot.data.dto.PersonDTO;
import org.marlisson.restwithspringboot.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonServices service;
    // public PersonServices service = new PersonServices();

    //@RequestMapping(method = RequestMethod.GET,
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PersonDTO create(@RequestBody PersonDTO person) {

        return service.create(person);
    }

    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PersonDTO update(
            @PathVariable Long id,
            @RequestBody PersonDTO person) {

        return service.update(id, person);
    }

    @DeleteMapping(path = "/{id}")
    // public void delete(@PathVariable Long id) { // Envia 200
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // Opcional. Envia 204
    }
}
