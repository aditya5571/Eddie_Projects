package com.rmit.sept.ordermicroservices.Repositories;

import com.rmit.sept.ordermicroservices.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>{
    Transaction getById(Long id);
}
