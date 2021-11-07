package com.rmit.sept.bk_bookcatalogservices.validator;

import com.rmit.sept.bk_bookcatalogservices.model.Ad;
import com.rmit.sept.bk_bookcatalogservices.model.Condition;
import com.rmit.sept.bk_bookcatalogservices.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AdValidator implements Validator {
    @Autowired
    BookService bookservice;


    @Override
    public boolean supports(Class<?> aClass) {
        return Ad.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Ad ad = (Ad) object;

        if(!bookservice.containsByIsbn(ad.getIsbn())){
            errors.rejectValue("isbn","invalid", "Book doesn't exist");
        }

        boolean validCondition = false;
        for(Condition condition : Condition.values()){
            if(condition.toString().equalsIgnoreCase(ad.getCondition())){
                validCondition = true;
                break;
            }
        }

        if(!validCondition){
            errors.rejectValue("condition","invalid", "Invalid value for condition");

        }



    }
}
