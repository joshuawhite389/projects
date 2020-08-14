package com.techelevator.controller;

import javax.validation.Valid;

import com.techelevator.dao.BookDAO;
import com.techelevator.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.dao.UserDAO;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;

import java.util.List;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDAO userDAO;
    private BookDAO bookDAO;

    public AuthenticationController(TokenProvider tokenProvider,
                                    AuthenticationManagerBuilder authenticationManagerBuilder, UserDAO userDAO, BookDAO bookDAO) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDto) {

        //------Auth-------------------------------------------------------------------------------------//
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        //------LOGIN OBJ INFO-------------------------------------------------------------------------------------//
        User user = userDAO.findByUsername(loginDto.getUsername());
        UserInFamily userDetails = userDAO.getFamilyMember(user.getUsername());
        List<BookDTO> userBooks = bookDAO.getUserBooks(user.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new LoginResponse(jwt, user, userDetails, userBooks), httpHeaders, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterUserDTO newUser) {
        //------REGISTER AND CHECK FOR REDUNDANCY-------------------------------------------------------------------//
        if (userDAO.usernameExists(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists.");
        }
        if (userDAO.familyNameExists(newUser.getFamilyName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Family Already Exists.");
        } else {
            userDAO.create(newUser.getUsername(), newUser.getPassword(), newUser.getRole(), newUser.getFirstName(),
                    newUser.getLastName(), newUser.getAge());
            userDAO.addNewFamily(newUser.getFamilyName(), newUser.getUsername());
        }

    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class LoginResponse {

        private String token;
        private User user;
        private UserInFamily userDetails;
        private List<BookDTO> userBooks;

        LoginResponse(String token, User user, UserInFamily userDetails, List<BookDTO> userBooks) {
            this.token = token;
            this.user = user;
            this.userDetails = userDetails;
            this.userBooks = userBooks;
        }

        @JsonProperty("token")
        String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }

        @JsonProperty("user")
        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @JsonProperty("userDetails")
        public UserInFamily getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(UserInFamily userDetails) {
            this.userDetails = userDetails;
        }

        @JsonProperty("userBooks")
        public List<BookDTO> getUserBooks() {
            return userBooks;
        }

        public void setUserBooks(List<BookDTO> userBooks) {
            this.userBooks = userBooks;
        }
    }
}
