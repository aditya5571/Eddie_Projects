package com.rmit.sept.bk_reviewservices.Repositories;

import com.rmit.sept.bk_reviewservices.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

}