package org.marlisson.restwithspringboot.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.marlisson.restwithspringboot.controllers.docs.PersonControllerDocs;
import org.marlisson.restwithspringboot.data.dto.PersonDTO;
import org.marlisson.restwithspringboot.file.exporter.MediaTypes;
import org.marlisson.restwithspringboot.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/exportPage", produces = {
                MediaTypes.APPLICATION_XLSX_VALUE,
                MediaTypes.APPLICATION_CSV_VALUE,
                MediaTypes.APPLICATION_PDF_VALUE
    })
    @Override
    public ResponseEntity<Resource> exportPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            HttpServletRequest request
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        Resource file = service.exportPage(pageable, acceptHeader);

        Map<String, String> extensionMap = Map.of(
                MediaTypes.APPLICATION_XLSX_VALUE, ".xlsx",
                MediaTypes.APPLICATION_CSV_VALUE, ".csv",
                MediaTypes.APPLICATION_PDF_VALUE, ".pdf"
        );

        String fileExtension = extensionMap.getOrDefault(acceptHeader, "");
        String contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";
        var filename = "people_exporter" + fileExtension;

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"")
            .body(file);
    }

    @GetMapping(value = "/findPeopleByName/{firstName}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
            @PathVariable String firstName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(service.findByName(firstName, pageable));
    }

    @GetMapping(value = "/export/{id}",
            produces = MediaTypes.APPLICATION_PDF_VALUE
    )
    @Override
    public ResponseEntity<Resource> export(Long id, HttpServletRequest request) {
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        Resource file = service.exportPerson(id, acceptHeader);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(acceptHeader))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=person.pdf")
                .body(file);
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

    @PostMapping(
            value = "/massCreation",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public List<PersonDTO> massCreation(@RequestParam("file") MultipartFile file) {
        return service.massCreation(file);
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

    @PatchMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO disablePerson(@PathVariable Long id) {
        return service.disablePerson(id);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // Opcional. Envia 204
    }
}
