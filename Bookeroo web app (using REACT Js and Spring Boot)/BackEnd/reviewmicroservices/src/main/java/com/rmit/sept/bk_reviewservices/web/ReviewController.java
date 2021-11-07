package com.rmit.sept.bk_reviewservices.web;

import com.rmit.sept.bk_reviewservices.model.Review;
import com.rmit.sept.bk_reviewservices.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final Logger log = LoggerFactory.getLogger(ReviewController.class);


    @Autowired
    ReviewService reviewService;

    @GetMapping("/all_reviews")
    private List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/review_by_ISBN/{ISBN}")
    private List<Review> getReviewsByBookISBN(@PathVariable("ISBN") long ISBN) {
        return reviewService.getAllReviewsByBookISBN(ISBN);
    }

    @DeleteMapping("/books/{BookId}/{id}")
    private void deleteReviewsByBookId(@PathVariable("id") long id) {
        reviewService.deleteReview(id);
    }

    @RequestMapping(value = "/create")
    public Long saveReview(@RequestBody Review review) {
        reviewService.saveReview(review);
        return review.getId();
    }

    @PutMapping("/books")
    private Review updateReview(@RequestBody Review review) {
        reviewService.updateReview(review, review.getId());
        return review;
    }
}
