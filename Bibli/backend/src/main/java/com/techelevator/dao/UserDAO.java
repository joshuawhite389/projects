package com.techelevator.dao;

import com.techelevator.model.User;
import com.techelevator.model.UserInFamily;

import java.util.List;

public interface UserDAO {

    User findByUsername(String username);

    boolean usernameExists(String username);
    
    boolean create(String username, String password, String role, String firstName, String lastName, String age);
    
    boolean createUserInfo(int userId, String firstName, String lastName, String dob);
    
	boolean addNewFamily(String familyName, String username);
	
	boolean familyNameExists(String familyName);

    List<UserInFamily> getFamilyMembers(String familyName);

    UserInFamily getFamilyMember(String familyName);

    boolean addToFamily(String familyName, String username);


}
