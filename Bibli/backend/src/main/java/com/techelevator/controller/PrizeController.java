package com.techelevator.controller;

import com.techelevator.dao.PrizeDAO;
import com.techelevator.model.FamilyPrizesDTO;
import com.techelevator.model.PrizeDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin
public class PrizeController {
    private PrizeDAO prizeDAO;

    public PrizeController(PrizeDAO prizeDAO) {
        this.prizeDAO = prizeDAO;
    }

    @RequestMapping(value = "/add-prize", method = RequestMethod.POST)
    public void addPrize(@Valid @RequestBody PrizeDTO prize) {
        //----FAMILY PRIZE.. DEFAULTS TO BOTH CHILD AND PARENTS--------------------------------------------------------//
        prizeDAO.addPrize(prize);
    }

    @RequestMapping(value = "/get-prizes/{family}", method = RequestMethod.GET)
    public List<PrizeDTO> getPrizes(@PathVariable("family") String family) {
        //-----ALL OF A FAMILIES PRIZES------------------------------------------------------------------------------//
        return prizeDAO.getPrizes(family);
    }


    @RequestMapping(value = "/award-user/{family}", method = RequestMethod.POST)
    public void addPrizeWon(@Valid @PathVariable("family") String family, @RequestBody FamilyPrizesDTO prize) {
        //-----PRIZE ID ADDED TO DB ENTRY W/ GIVEN USERNAME----------------------------------------------------------//
        prizeDAO.addPrizesWon(prize, family);
    }


    @RequestMapping(value = "/list-awards/{family}", method = RequestMethod.GET)
    public List<FamilyPrizesDTO> getPrizeInfo(@PathVariable("family") String family) {
        //-----FAMILY MEMBERS WITH AWARDS THEY HAVE---------------------------------------------------------------//
        return prizeDAO.getFamilyPrizes(family);
    }


    @RequestMapping(value = "/delete-prize/{prizeId}", method = RequestMethod.DELETE)
    public void deletePrize(@PathVariable("prizeId") int prizeId) {
        //-----REMOVE PRIZE COMPLETELY FROM DB-----------------------------------------------------------------//
        if (prizeDAO.deletePrize(prizeId)) {
            System.out.println("Delete Successful");
        }
        System.out.println("Error Deleting Prize with Id: " + prizeId);
    }

    @RequestMapping(value = "/update-prize", method = RequestMethod.PUT)
    public void updatePrize(@Valid @RequestBody PrizeDTO prize) {
        //-----CHANGE ANY INFO ABOUT A EXISTING PRIZE----------------------------------------------------------//
        if (prizeDAO.updatePrize(prize)) {
            System.out.println("Update Successful");
        }
        System.out.println("Error Updating Prize with Id:" + prize.getPrizeId() + " Name:" + prize.getName());
    }
}
