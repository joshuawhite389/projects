package com.techelevator.model;

import java.util.List;

public class UserInFamily {

    private String username;
    private String role;
    private String firstName;
    private String lastName;
    private String age;
    private String familyName;
    private int user_id;
    private List<BookDTO> books;
    private List<FamilyPrizesDTO> familyPrizeDTO;


    public UserInFamily() {}

    public UserInFamily(String familyName, String username, String role, String firstName, String lastName, String age, int user_id, List<BookDTO> books, List<FamilyPrizesDTO> familyPrizeDTO) {
        this.familyName = familyName;
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.user_id = user_id;
        this.books = books;
        this.familyPrizeDTO = familyPrizeDTO;
    }

    public List<FamilyPrizesDTO> getFamilyPrizeDTO() { return familyPrizeDTO; }

    public void setFamilyPrizeDTO(List<FamilyPrizesDTO> familyPrizeDTO) { this.familyPrizeDTO = familyPrizeDTO; }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



}
