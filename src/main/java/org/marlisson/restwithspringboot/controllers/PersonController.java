package org.marlisson.restwithspringboot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.marlisson.restwithspringboot.data.dto.PersonDTO;
import org.marlisson.restwithspringboot.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Manging People")
public class PersonController {

    @Autowired
    public PersonServices service;
    // public PersonServices service = new PersonServices();

    //@RequestMapping(method = RequestMethod.GET,
    @GetMapping(produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_YAML_VALUE })
    @Operation(summary = "Find All People",
        description="Finds All People",
        tags={"People"},
        responses={
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = {
                    @Content(
                        mediaType=MediaType.APPLICATION_JSON_VALUE,
                        array=@ArraySchema(schema=@Schema(implementation = PersonDTO.class))
                    )
                }),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_YAML_VALUE })
    @Operation(summary = "Find A Person",
        description="Finds a specific person by its ID",
        tags={"People"},
        responses={
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(mediaType=MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
    public PersonDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping(
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE }
    )
    @Operation(summary = "Add a new Person",
        description="Adds a new person by passing in a JSON, XML or YAML representation of the person.",
        tags={"People"},
        responses={
            @ApiResponse(
                description = "Success",
                responseCode = "201",
                content = @Content(mediaType=MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
    public PersonDTO create(@RequestBody PersonDTO person) {

        return service.create(person);
    }

    @PutMapping(
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE }
    )
    @Operation(summary = "Update a Person's information",
        description="Updates a person's information by passing in a JSON, XML or YAML representation of the updated person.",
        tags={"People"},
        responses={
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(mediaType=MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
    public PersonDTO update(@RequestBody PersonDTO person) {

        return service.update(person);
    }

    @DeleteMapping(path = "/{id}")
    // public void delete(@PathVariable Long id) { // Envia 200
    @Operation(summary = "Create A Person",
        description="Creates a Person with the given payload",
        tags={"People"},
        responses={
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // Opcional. Envia 204
    }
}
