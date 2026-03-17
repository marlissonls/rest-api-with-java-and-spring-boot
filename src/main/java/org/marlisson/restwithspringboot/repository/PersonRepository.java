package org.marlisson.restwithspringboot.repository;

import org.marlisson.restwithspringboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Modifying garante ACID?
    // clearAutomatically serve para limpar cash para novas consultas.
    // (o metodo repository.findById é executado duas vezes em services.disablePerson)
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);
}
