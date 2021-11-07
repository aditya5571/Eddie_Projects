package com.rmit.sept.bk_bookcatalogservices.Repositories;

import com.rmit.sept.bk_bookcatalogservices.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    boolean existsByIsbn(Long isbn);
    Book findByIsbn(Long isbn);
    List<Book> findByNameIgnoreCaseContaining(String name);
    List<Book> findByAuthorIgnoreCaseContaining(String author);


}
