package org.marlisson.restwithspringboot.data.dto;

import org.marlisson.restwithspringboot.exception.ResourceNotFoundException;
import org.marlisson.restwithspringboot.model.Person;
import org.marlisson.restwithspringboot.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonDTO {

    private final Logger logger =  LoggerFactory.getLogger(PersonDTO.class.getName());
    //private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository repository;


    public List<Person> findAll() {
        logger.info("Finding all Person!");

        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one Person!");
        // findById originalmente Retorna um Option do tipo Person
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Person create(Person person) {
        logger.info("Creating one Person!");
        return repository.save(person);
    }

    public Person update(Long id, Person person) {
        logger.info("Updating one Person!");
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
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
