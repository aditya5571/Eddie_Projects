package com.rmit.sept.ordermicroservices.Repositories;


import com.rmit.sept.ordermicroservices.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findByUserId(Long id);

}
