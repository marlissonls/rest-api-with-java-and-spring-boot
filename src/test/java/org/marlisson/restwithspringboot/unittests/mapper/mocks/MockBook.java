package org.marlisson.restwithspringboot.unittests.mapper.mocks;

import org.marlisson.restwithspringboot.data.dto.BookDTO;
import org.marlisson.restwithspringboot.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> persons = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockDTO(i));
        }
        return persons;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Author Test " + number);
        book.setTitle("Title Test " + number);
        book.setPrice(25D + number);
        book.setLaunchDate(new Date());
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setId(number.longValue());
        book.setAuthor("Author Test " + number);
        book.setTitle("Title Test " + number);
        book.setPrice(25D + number);
        book.setLaunchDate(new Date());
        return book;
    }

}