package org.marlisson.restwithspringboot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.marlisson.restwithspringboot.data.dto.BookDTO;
import org.marlisson.restwithspringboot.exception.RequiredObjectIsNullException;
import org.marlisson.restwithspringboot.model.Book;
import org.marlisson.restwithspringboot.repository.BookRepository;
import org.marlisson.restwithspringboot.unittests.mapper.mocks.MockBook;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook input;

    @InjectMocks
    private BookServices service;

    @Mock
    BookRepository repository;


    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        var result = service.findById(1l);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream().anyMatch(
            link -> link.getRel().value().equals("self")
                    && link.getHref().endsWith("api/book/v1/1")
                    && link.getType().equals("GET")
            ),
            "Self link for GET api/book/v1/1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
            link -> link.getRel().value().equals("findAll")
                    && link.getHref().endsWith("api/book/v1")
                    && link.getType().equals("GET")
            ),
            "FindAll link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
            link -> link.getRel().value().equals("create")
                    && link.getHref().endsWith("api/book/v1")
                    && link.getType().equals("POST")
            ),
            "Create link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
            link -> link.getRel().value().equals("update")
                    && link.getHref().endsWith("api/book/v1")
                    && link.getType().equals("PUT")
            ),
            "Update link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
            link -> link.getRel().value().equals("delete")
                    && link.getHref().endsWith("api/book/v1/1")
                    && link.getType().equals("DELETE")
            ),
            "Delete link for GET api/book/v1 should exist"
        );

        assertEquals("Author Test 1", result.getAuthor());
        assertEquals("Title Test 1", result.getTitle());
        assertEquals(26D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void create() {
        BookDTO dto = input.mockDTO(1);

        Book entity = input.mockEntity(1);

        when(repository.save(any(Book.class))).thenReturn(entity);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("api/book/v1/1")
                                && link.getType().equals("GET")
                ),
                "Self link for GET api/book/v1/1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("GET")
                ),
                "FindAll link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("POST")
                ),
                "Create link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("PUT")
                ),
                "Update link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("api/book/v1/1")
                                && link.getType().equals("DELETE")
                ),
                "Delete link for GET api/book/v1 should exist"
        );

        assertEquals("Author Test 1", result.getAuthor());
        assertEquals("Title Test 1", result.getTitle());
        assertEquals(26D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
            () -> {
                service.create(null);
            });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("api/book/v1/1")
                                && link.getType().equals("GET")
                ),
                "Self link for GET api/book/v1/1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("GET")
                ),
                "FindAll link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("POST")
                ),
                "Create link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("PUT")
                ),
                "Update link for GET api/book/v1 should exist"
        );

        assertTrue(result.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("api/book/v1/1")
                                && link.getType().equals("DELETE")
                ),
                "Delete link for GET api/book/v1 should exist"
        );

        assertEquals("Author Test 1", result.getAuthor());
        assertEquals("Title Test 1", result.getTitle());
        assertEquals(26D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        service.delete(1l);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<BookDTO> people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var bookOne = people.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getLinks());

        assertTrue(bookOne.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("api/book/v1/1")
                                && link.getType().equals("GET")
                ),
                "Self link for GET api/book/v1/1 should exist"
        );

        assertTrue(bookOne.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("GET")
                ),
                "FindAll link for GET api/book/v1 should exist"
        );

        assertTrue(bookOne.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("POST")
                ),
                "Create link for GET api/book/v1 should exist"
        );

        assertTrue(bookOne.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("PUT")
                ),
                "Update link for GET api/book/v1 should exist"
        );

        assertTrue(bookOne.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("api/book/v1/1")
                                && link.getType().equals("DELETE")
                ),
                "Delete link for GET api/book/v1 should exist"
        );

        assertEquals("Author Test 1", bookOne.getAuthor());
        assertEquals("Title Test 1", bookOne.getTitle());
        assertEquals(26D, bookOne.getPrice());
        assertNotNull(bookOne.getLaunchDate());


        var bookFour = people.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getId());
        assertNotNull(bookFour.getLinks());

        assertTrue(bookFour.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("api/book/v1/4")
                                && link.getType().equals("GET")
                ),
                "Self link for GET api/book/v1/1 should exist"
        );

        assertTrue(bookFour.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("GET")
                ),
                "FindAll link for GET api/book/v1 should exist"
        );

        assertTrue(bookFour.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("POST")
                ),
                "Create link for GET api/book/v1 should exist"
        );

        assertTrue(bookFour.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("PUT")
                ),
                "Update link for GET api/book/v1 should exist"
        );

        assertTrue(bookFour.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("api/book/v1/4")
                                && link.getType().equals("DELETE")
                ),
                "Delete link for GET api/book/v1 should exist"
        );

        assertEquals("Author Test 4", bookFour.getAuthor());
        assertEquals("Title Test 4", bookFour.getTitle());
        assertEquals(29D, bookFour.getPrice());
        assertNotNull(bookFour.getLaunchDate());


        var bookSeven = people.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getId());
        assertNotNull(bookSeven.getLinks());

        assertTrue(bookSeven.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("api/book/v1/7")
                                && link.getType().equals("GET")
                ),
                "Self link for GET api/book/v1/1 should exist"
        );

        assertTrue(bookSeven.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("GET")
                ),
                "FindAll link for GET api/book/v1 should exist"
        );

        assertTrue(bookSeven.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("POST")
                ),
                "Create link for GET api/book/v1 should exist"
        );

        assertTrue(bookSeven.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("api/book/v1")
                                && link.getType().equals("PUT")
                ),
                "Update link for GET api/book/v1 should exist"
        );

        assertTrue(bookSeven.getLinks().stream().anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("api/book/v1/7")
                                && link.getType().equals("DELETE")
                ),
                "Delete link for GET api/book/v1 should exist"
        );

        assertEquals("Author Test 7", bookSeven.getAuthor());
        assertEquals("Title Test 7", bookSeven.getTitle());
        assertEquals(32D, bookSeven.getPrice());
        assertNotNull(bookSeven.getLaunchDate());
    }
}