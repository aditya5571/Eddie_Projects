package com.rmit.sept.bk_loginservices.validator;

import com.rmit.sept.bk_loginservices.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        User user = (User) object;

        if(user.getPassword().length() <6){
            errors.rejectValue("password","Length", "Password must be at least 6 characters");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match", "Passwords must match");
        }

        if (user.getUserType().equals("Publisher") || user.getUserType().equals("Shop owner")) {
            if (user.getAbnNumber().length() < 11 || user.getAbnNumber().length() > 11) {
                errors.rejectValue("abnNumber", "Length", "ABN Number must be at 11 digits");
            }
        }

        if (user.getPhoneNumber().length() < 10 || user.getPhoneNumber().length() > 10){
            errors.rejectValue("phoneNumber","Length", "Phone number must be at 10 digits");
        }

        if (!user.getUserType().equals("Publisher") && !user.getUserType().equals("Customer") && !user.getUserType().equals("Shop owner")) {
            errors.rejectValue("userType","Category", "Role must be either publisher, shop owner or customer");
        }

    }
}
