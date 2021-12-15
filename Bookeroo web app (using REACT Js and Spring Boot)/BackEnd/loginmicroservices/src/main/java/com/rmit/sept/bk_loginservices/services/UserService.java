package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.exceptions.UsernameAlreadyExistsException;
import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.payload.UserID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){



        /*  newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            Username has to be unique (exception)
            Make sure that password and confirmPassword match
            We don't persist or show the confirmPassword
        */
        User userToSave = new User();
        userToSave.setFullName(newUser.getFullName());
        userToSave.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userToSave.setAddress(newUser.getAddress());
        userToSave.setUserType(newUser.getUserType());

        // Make sure that password and confirmPassword match
        // We don't persist or show the confirmPassword
        userToSave.setConfirmPassword("");


        // Setting the user to get approval for admin if they are a publisher or shop publisher
        if (newUser.getUserType().equals("Publisher") || newUser.getUserType().equals("Shop owner")) {
            userToSave.setApproved(false);
        } else {
            userToSave.setApproved(true);
        }

        // ABN has to be unique (exception)
        // Phone number has to be unique (exception)
        userToSave.setUsername(newUser.getUsername());
        userRepository.findAll().forEach(user -> {
            if (user.getUsername().equals(newUser.getUsername())) {
                throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
            }
        });
        userToSave.setPhoneNumber(newUser.getPhoneNumber());
        userRepository.findAll().forEach(user -> {
            if (user.getPhoneNumber() != null && newUser.getPhoneNumber() != null && user.getPhoneNumber().equals(newUser.getPhoneNumber())) {
                throw new UsernameAlreadyExistsException("Phone Number '" + newUser.getPhoneNumber() + "' already exists");
            }
        });
        if (!newUser.getAbnNumber().equals("")) {
            userToSave.setAbn_number(newUser.getAbnNumber());
            userRepository.findAll().forEach(user -> {
                if (user.getAbnNumber() != null && newUser.getAbnNumber() != null && user.getAbnNumber().equals(newUser.getAbnNumber())) {
                    throw new UsernameAlreadyExistsException("ABN Number '" + newUser.getAbnNumber() + "' already exists");
                }
            });
        } else {
            userToSave.setAbn_number(null);
        }

        try {
            return userRepository.save(userToSave);
        }catch (Exception e) {
            throw new UsernameAlreadyExistsException("Something went wrong");
        }
    }

    public void updateUser(User user) {
        log.info("User "+ user.getUsername() + " Successfully updated");
        userRepository.save(user);
    }


    // Returns a specific user by their username(email)
    public User retrieveUserUsername(String username) {
        log.info("User successfully retrieved");
        return  userRepository.findByUsername(username);
    }

    public User retreiveUserbyUserId(Long userId){ return userRepository.getById(userId);}

    // Returns a list of all the users that are not the admins
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (!user.getUserType().equals("Admin")) {
                allUsers.add(user);
            }
        });
        return allUsers;

    }

    // Returns a list of all the publishers and shop owners that have not been approved yet
    public List<User> getAllUnapprovedUsers() {
        List<User> unapprovedUsers = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (!user.getApproved() && !user.getBanned()) {
                unapprovedUsers.add(user);
            }
        });
        log.info("Returning all the unapproved user");
        return unapprovedUsers;
    }

    // Changes the publishers/shop owners account approval to be true, so the publishers/shop owners can log in
    public boolean setApproval(@Valid @RequestBody UserID id) {
        User user = userRepository.getById(id.getId());
        if (user == null) {
            log.info("User is null");
            return false;
        } else if (!user.getApproved()) {
            user.setApproved(true);
            updateUser(user);
            log.info("User approved successfully");
        }
        return true;
    }

    // Changes the approval to false, in the case the admin made a mistake
    public boolean setUnapproval(long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            log.info("User is null");
            return false;
        } else if (user.getApproved()) {
            user.setApproved(false);
            updateUser(user);
            log.info("User unapproved successfully");
        }
        return true;
    }

    // Changes the ban boolean of a user to true, so users cannot log in anymore
    public boolean banUser(@Valid UserID id) {
        User user = userRepository.getById(id.getId());
        if (user == null) {
            log.info("User is null");
            return false;
        } else {
            user.setBanned(true);
            updateUser(user);
            log.info("User ban successfully");
        }
        return true;
    }

    // Changes the ban boolean of a user to false in the case the admin makes a mistake
    public boolean unbanUser(long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            log.info("User is null");
            return false;
        } else {
            user.setBanned(false);
            updateUser(user);
            log.info("User unban successfully");
        }
        return true;
    }
    

}
