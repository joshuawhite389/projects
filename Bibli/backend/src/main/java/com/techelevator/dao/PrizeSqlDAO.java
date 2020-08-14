package com.techelevator.dao;

import com.techelevator.model.FamilyPrizesDTO;
import com.techelevator.model.PrizeDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrizeSqlDAO implements PrizeDAO {

    private JdbcTemplate jdbcTemplate;

    public PrizeSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addPrize(PrizeDTO prize) {
        String sql = "INSERT INTO prizes (name, description, milestone, user_role, max_prizes, start_date, end_date, " +
                "family_id, is_active) VALUES (?,?,?,?,?,?,?, (SELECT family_id FROM family WHERE family_name = ?),?)";
        return 0 != jdbcTemplate.update(sql, prize.getName(), prize.getDescription(), prize.getMilestone(),
                prize.getUserRole(), prize.getMaxPrizes(), prize.getStartDate(), prize.getEndDate(), prize.getFamilyName(), prize.getIsActive());
    }

    @Override
    public boolean addPrizesWon(FamilyPrizesDTO familyPrize, String familyName) {
        String sql = "INSERT INTO family_prizes (username, family_id, prize_id) VALUES (?, (SELECT family_id FROM family WHERE family_name = ?), ?)";
        return 0 != jdbcTemplate.update(sql, familyPrize.getUsername(), familyName, familyPrize.getPrizeId());
    }

    @Override
    public List<PrizeDTO> getPrizes(String familyName) {
        List<PrizeDTO> familyPrizes = new ArrayList<>();
        String sql = "SELECT prize_id, name, description, milestone, user_role, max_prizes, start_date, end_date, is_active, family.family_name " +
                "FROM prizes INNER JOIN family ON family.family_id = prizes.family_id WHERE family_name = ?";

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, familyName);
        while (rs.next()) {
            familyPrizes.add(mapToPrize(rs));
        }
        return familyPrizes;
    }

    @Override
    public List<FamilyPrizesDTO> getFamilyPrizes(String familyName) {
        List<FamilyPrizesDTO> familyPrizesDTOCompleted = new ArrayList<>();
        String sql = "SELECT family_prizes.prize_id, family.family_id, username, family_prize_id, prizes.name, prizes.description" +
                " FROM family_prizes INNER JOIN family ON family.family_id = family_prizes.family_id "
                + "INNER JOIN prizes ON prizes.prize_id = family_prizes.prize_id "
                + "WHERE family_name = ?";

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, familyName);
        while (rs.next()) {
            familyPrizesDTOCompleted.add(mapToFamilyPrize(rs));
        }
        return familyPrizesDTOCompleted;

    }

    @Override
    public boolean deletePrize(int prizeId) {
        String sql = "DELETE FROM prizes WHERE prize_id = ?";
        return 1 == jdbcTemplate.update(sql, prizeId);  //if 1 row effected return true

    }

    @Override
    public boolean updatePrize(PrizeDTO prize) {
        String sql = "UPDATE prizes SET name = ?, description = ?, milestone = ?, user_role = ?, max_prizes = ?, start_date = ?, end_date = ?, " +
                " is_active = ? WHERE prize_id = ? ";
        return 1 == jdbcTemplate.update(sql, prize.getName(), prize.getDescription(), prize.getMilestone(),
                prize.getUserRole(), prize.getMaxPrizes(), prize.getStartDate(), prize.getEndDate(), prize.getIsActive(), prize.getPrizeId());
    }

    private PrizeDTO mapToPrize(SqlRowSet rs) {
        PrizeDTO prize = new PrizeDTO();
        prize.setPrizeId(rs.getInt("prize_id"));
        prize.setName(rs.getString("name"));
        prize.setDescription(rs.getString("description"));
        prize.setMilestone(rs.getInt("milestone"));
        prize.setUserRole(rs.getString("user_role"));
        prize.setMaxPrizes(rs.getInt("max_prizes"));
        prize.setStartDate(rs.getDate("start_date"));
        prize.setEndDate(rs.getDate("end_date"));
        prize.setActive(rs.getBoolean("is_active"));
        prize.setFamilyName(rs.getString("family_name"));
        return prize;
    }

    private FamilyPrizesDTO mapToFamilyPrize(SqlRowSet rs) {
        FamilyPrizesDTO prize = new FamilyPrizesDTO();
        prize.setFamilyId(rs.getInt("family_id"));
        prize.setPrizeId(rs.getInt("prize_id"));
        prize.setFamilyPrizeId(rs.getInt("family_prize_id"));
        prize.setUsername(rs.getString("username"));
        prize.setPrizeName(rs.getString("name"));
        prize.setPrizeDescription(rs.getString("description"));
        return prize;
    }


}
