package com.rmit.sept.bk_reviewservices.services;

import com.rmit.sept.bk_reviewservices.Repositories.ReviewRepository;
import com.rmit.sept.bk_reviewservices.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final Logger log = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    ReviewRepository reviewrepository;

    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        reviewrepository.findAll().forEach(reviewTemp -> reviews.add(reviewTemp));
        return reviews;
    }

    public List<Review> getAllReviewsByBookISBN(Long BookISBN) {
        List<Review> temp = getAllReviews();
        List<Review> reviews = new ArrayList<>();
        for (Review rev : temp) {
            if (rev.getBookISBN().equals(BookISBN)) {
                reviews.add(rev);
            }
        }
        return reviews;
    }

    public Review saveReview(Review newReview) {
        return reviewrepository.save(newReview);
    }

    public void updateReview(Review review, Long id) {
        reviewrepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewrepository.deleteById(id);
    }
}
