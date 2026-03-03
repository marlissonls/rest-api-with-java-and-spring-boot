package org.marlisson.restwithspringboot.services;

import org.marlisson.restwithspringboot.data.dto.v1.PersonDTO;
import org.marlisson.restwithspringboot.data.dto.v2.PersonDTOV2;
import org.marlisson.restwithspringboot.exception.ResourceNotFoundException;
import static org.marlisson.restwithspringboot.mapper.ObjectMapper.parseListObjects;
import static org.marlisson.restwithspringboot.mapper.ObjectMapper.parseObject;

import org.marlisson.restwithspringboot.mapper.custom.PersonMapper;
import org.marlisson.restwithspringboot.model.Person;
import org.marlisson.restwithspringboot.repository.PersonRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;

@Service
public class PersonServices {

    private final Logger logger =  LoggerFactory.getLogger(PersonServices.class.getName());
    //private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;


    public List<PersonDTO> findAll() {
        logger.info("Finding all Person!");

        //return ObjectMapper.parseListObjects(repository.findAll(), PersonDTOV2.class);
        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");
        // findById originalmente Retorna um Option do tipo PersonDTOV2
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Creating one Person V2!");
        var entity = converter.convertDTOToEntity(person);

        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTO update(Long id, PersonDTO person) {
        logger.info("Updating one Person!");
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    /*private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname" + i);
        person.setLastName("Lastname" + i);
        person.setAddress("City" + i);
        person.setGender("Male" + i);
        return person;
    }*/
}
