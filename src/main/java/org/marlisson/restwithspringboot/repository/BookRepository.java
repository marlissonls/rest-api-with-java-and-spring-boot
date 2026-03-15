package org.marlisson.restwithspringboot.repository;

import org.marlisson.restwithspringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{


}
