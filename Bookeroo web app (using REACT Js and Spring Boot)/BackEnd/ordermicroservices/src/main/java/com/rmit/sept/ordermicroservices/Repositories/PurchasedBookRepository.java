package com.rmit.sept.ordermicroservices.Repositories;

import com.rmit.sept.ordermicroservices.model.PurchasedBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedBookRepository extends CrudRepository<PurchasedBook, Long>{
    PurchasedBook getById(Long id);
}
