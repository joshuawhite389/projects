package com.techelevator.controller;

import com.techelevator.dao.UserDAO;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.UserInFamily;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin
public class FamilyController {
    private UserDAO userDAO;

    public FamilyController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/family-info/{name}", method = RequestMethod.GET)
    public List<UserInFamily> getFamilyInfo(@PathVariable("name") String name) {
        //-----ALL MEMBERS OF A FAMILY W/ THEIR INFO INCLUDING BOOKS + PRIZES---------------------------------------------//
        return userDAO.getFamilyMembers(name);


    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public void addUser(@RequestBody RegisterUserDTO newUser) {
        //-----ADDING USER TO YOUR FAMILY (CHILD OR PARENT------------------------------------------------------------//
        if (userDAO.usernameExists(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists.");
        } else {
            userDAO.create(newUser.getUsername(), newUser.getPassword(), newUser.getRole(), newUser.getFirstName(),
                    newUser.getLastName(), newUser.getAge());
            userDAO.addToFamily(newUser.getFamilyName(), newUser.getUsername());
        }
    }

}
