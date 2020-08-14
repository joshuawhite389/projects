package com.techelevator.model;

public class FamilyPrizesDTO {

    private int familyPrizeId;
    private String username;
    private int familyId;
    private int prizeId;

    private String prizeName;
    private String prizeDescription;


    public int getFamilyPrizeId() {
        return familyPrizeId;
    }

    public void setFamilyPrizeId(int familyPrizeId) {
        this.familyPrizeId = familyPrizeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

}
