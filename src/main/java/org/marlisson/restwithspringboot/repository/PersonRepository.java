package org.marlisson.restwithspringboot.repository;

import org.marlisson.restwithspringboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>{


}
