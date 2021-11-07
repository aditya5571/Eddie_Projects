package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  void run(ApplicationArguments args) {
        // Creating 4 users and 1 admin for testing purposes in H2-database (as it is in-memory)
        User admin = new User();
        admin.setFullName("Admin User");
        admin.setUserType("Admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin321a"));
        admin.setUsername("admin@gmail.com");
        admin.setApproved(true);

        User customer = new User();
        customer.setFullName("Linda Vu");
        customer.setUserType("Customer");
        customer.setPassword(bCryptPasswordEncoder.encode("asdasdasd"));
        customer.setUsername("linda@gmail.com");
        customer.setAddress("420 Swamped Uni Street");
        customer.setPhoneNumber("0412121121");
        customer.setApproved(true);

        User shopOwner = new User();
        shopOwner.setFullName("Steve Irwin");
        shopOwner.setUserType("Shop owner");
        shopOwner.setPassword(bCryptPasswordEncoder.encode("asdasdasd"));
        shopOwner.setUsername("crikey@gmail.com");
        shopOwner.setAbn_number("12345678901");
        shopOwner.setAddress("42 Crocodile Street");
        shopOwner.setShopName("Australian Wildlife Bookstore");
        shopOwner.setPhoneNumber("0412123123");
        shopOwner.setApproved(false);

        User publisher = new User();
        publisher.setFullName("Jesus Christ");
        publisher.setUserType("Publisher");
        publisher.setPassword(bCryptPasswordEncoder.encode("asdasdasd"));
        publisher.setUsername("jesus@gmail.com");
        publisher.setAbn_number("77777777777");
        publisher.setAddress("77 Heaven Street");
        publisher.setShopName("We only sell the bible");
        publisher.setPhoneNumber("04777777");
        publisher.setUsername("jesus@gmail.com");
        publisher.setApproved(true);

        User publisher1 = new User();
        publisher1.setFullName("Finn");
        publisher1.setUserType("Publisher");
        publisher1.setPassword(bCryptPasswordEncoder.encode("asdasdasd"));
        publisher1.setUsername("h@gmail.com");
        publisher1.setAbn_number("12345612345");
        publisher1.setAddress("12 Adventure Street");
        publisher1.setShopName("The Land of Ooks");
        publisher1.setPhoneNumber("0412345123");
        publisher1.setApproved(false);

        userRepository.save(admin);
        userRepository.save(customer);
        userRepository.save(shopOwner);
        userRepository.save(publisher);
        userRepository.save(publisher1);

        log.info("User data loaded");
    }
}
