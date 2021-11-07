package com.rmit.sept.bk_bookcatalogservices.Repositories;

import com.rmit.sept.bk_bookcatalogservices.model.Ad;
import com.rmit.sept.bk_bookcatalogservices.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends CrudRepository<Ad, Long> {
    List<Ad> findAllByConditionAndIsbn(String condition, Long isbn);
    Ad getAdById(Long id);
}
