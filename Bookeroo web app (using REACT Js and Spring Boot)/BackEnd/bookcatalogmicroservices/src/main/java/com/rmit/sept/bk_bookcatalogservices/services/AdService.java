package com.rmit.sept.bk_bookcatalogservices.services;

import com.rmit.sept.bk_bookcatalogservices.Repositories.AdRepository;
import com.rmit.sept.bk_bookcatalogservices.Repositories.BookRepository;
import com.rmit.sept.bk_bookcatalogservices.exceptions.InvalidConditionException;
import com.rmit.sept.bk_bookcatalogservices.exceptions.InvalidIsbnException;
import com.rmit.sept.bk_bookcatalogservices.model.Ad;

import com.rmit.sept.bk_bookcatalogservices.model.Book;
import com.rmit.sept.bk_bookcatalogservices.model.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;


@Service
public class AdService {

    private final Logger log = LoggerFactory.getLogger(AdService.class);

    @Autowired
    AdRepository adRepository;

    @Autowired
    BookRepository bookRepository;

    public Ad saveAd(Ad newAd) {
        if(!validIsbn(newAd.getIsbn())){
            throw new InvalidIsbnException("Book with given Isbn doesn't exists");
        }
        if (!validCondition(newAd.getCondition())){
            throw new InvalidConditionException("Invalid Condition provided");
        }
        return adRepository.save(newAd);
    }

    public List<Ad> getAllAds(String condition, Long isbn){

        return adRepository.findAllByConditionAndIsbn(condition.toUpperCase(), isbn);
    }

    public Ad getAdById(Long ad_id){
        Ad ad =  adRepository.getAdById(ad_id);
        return ad;
    }

    public boolean validCondition(String testingCondition){
        for(Condition condition: Condition.values()){
            if(condition.toString().equalsIgnoreCase(testingCondition)){
                return true;
            }
        }
        return false;
    }

    public boolean validIsbn(Long testingIsbn){
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
        for(Book book: allBooks){
            if(testingIsbn.equals(book.getIsbn())){
                return true;
            }
        }
        return false;
    }

}