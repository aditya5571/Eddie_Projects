package com.rmit.sept.bk_reviewservices.security;

import com.rmit.sept.bk_reviewservices.Repositories.ReviewRepository;
import com.rmit.sept.bk_reviewservices.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private ReviewRepository reviewRepository;

    @Autowired
    public DataLoader(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void run(ApplicationArguments args) {
        Review review1 = new Review();
        review1.setBookISBN(((long) 99798));
        review1.setUserName("Alexander the dude");
        review1.setRating("4");
        review1.setReview("Loved it!");

        reviewRepository.save(review1);
    }
}
