package com.rmit.sept.bk_loginservices.web;


import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.payload.JWTLoginSucessReponse;
import com.rmit.sept.bk_loginservices.payload.LoginRequest;
import com.rmit.sept.bk_loginservices.payload.UserID;
import com.rmit.sept.bk_loginservices.security.JwtTokenProvider;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;
import com.rmit.sept.bk_loginservices.services.UserService;
import com.rmit.sept.bk_loginservices.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.rmit.sept.bk_loginservices.security.SecurityConstant.TOKEN_PREFIX;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/{userId}")
    private User getUserInfo(@PathVariable("userId") Long userId) {
        log.info("GET Request to /"+ userId);
        return userService.retreiveUserbyUserId(userId);

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        // Validate passwords match
        userValidator.validate(user,result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;

        User newUser = userService.saveUser(user);

        log.info("POST Request to /register" + user.getUsername());
        return  new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Returns an invalid or a valid jwt based on boolean requirements and if the password is correct
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        // Has to meet these requirements to be properly authenticated, found based on username
        User user = userService.retrieveUserUsername(loginRequest.getUsername());
        if ((user == null) || (!user.getApproved()) || (user.getBanned())) {
             // Create an authentication that is bound to fail to validate
             Authentication badAuthentication = authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(
                             null,
                             null
                     )
             );
             // An invalid jwt is created.
             SecurityContextHolder.getContext().setAuthentication(badAuthentication);
             String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(badAuthentication);
            log.info("POST Request to /login"+ loginRequest.getUsername());
             return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));

         } else {
            // Properly validates the user here
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            // Depending on the authentication, an invalid or valid jwt is returned
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);
            log.info("POST Request to /login"+ loginRequest.getUsername());
            return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
        }

    }

    @PostMapping("/requests")
    public List<User> approveUser(@Valid @RequestBody UserID id){
        userService.setApproval(id);
        log.info("POST Request to /requests"+ "User ID =" + id);
        return userService.getAllUnapprovedUsers();
    }

    // Updates the ban on user model
    @PostMapping("/ban")
    public List<User> banUser(@Valid @RequestBody UserID id){
        userService.banUser(id);
        log.info("POST Request to /ban"+ "User ID =" + id);
        return userService.getAllUnapprovedUsers();

    }

    // Retrieves all the unapproved users for frontend
    @RequestMapping("/requests")
    public List<User> getAllUnapprovedUsers(){
        log.info("GET Request to /requests" );
        return userService.getAllUnapprovedUsers();
    }

    // Retrieves all users for frontend
    @RequestMapping("/all")
    public List<User> getAllUser(){
        log.info("GET Request to /all" );
        return userService.getAllUsers();
    }


}
