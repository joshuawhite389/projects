package com.techelevator.dao;

import com.techelevator.model.FamilyPrizesDTO;
import com.techelevator.model.PrizeDTO;

import java.util.List;

public interface PrizeDAO {
    boolean addPrize(PrizeDTO prizeDTO);
    boolean addPrizesWon(FamilyPrizesDTO familyPrize, String familyName);
    List<PrizeDTO> getPrizes(String familyName);
    List<FamilyPrizesDTO> getFamilyPrizes(String familyName);
    public boolean deletePrize(int prizeId);
    public boolean updatePrize(PrizeDTO prize);
    
}
