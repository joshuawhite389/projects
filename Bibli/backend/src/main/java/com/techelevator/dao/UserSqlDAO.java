package com.techelevator.dao;

import com.techelevator.model.UserInFamily;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.model.User;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserSqlDAO implements UserDAO {

    private JdbcTemplate jdbcTemplate;


    private static final String ALL_FIELDS = "user_id, username, password_hash, role";
    private BookDAO bookDAO;
    private PrizeDAO prizeDAO;

    public UserSqlDAO(JdbcTemplate jdbcTemplate, BookDAO bookDAO, PrizeDAO prizeDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookDAO = bookDAO;
        this.prizeDAO = prizeDAO;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT " + ALL_FIELDS + " FROM users WHERE username = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, username);
        if (rs.next()) {
            return mapRowToUser(rs);
        } else {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }
    }

    @Override
    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, username);
        return rs.next();
    }

    @Override
    public boolean create(String username, String password, String role, String firstName, String lastName,
                          String age) {
        boolean userCreated = false;
        String sql = "INSERT INTO users (username,password_hash,role, first_name, last_name, age) VALUES (?,?,?,?,?,?); ";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = "ROLE_" + role.toUpperCase();

        try {
            int count = jdbcTemplate.update(sql, username, password_hash, ssRole, firstName, lastName, age);
            userCreated = (count == 1);
        } catch (DataAccessException e) {
            // userCreated remains false
        }
        return userCreated;
    }

    @Override
    public boolean createUserInfo(int userId, String firstName, String lastName, String dob) {
        boolean userInfoCreated = false;
        String sql = "INSERT INTO user_info (user_id, first_name, last_name, date_of_birth) VALUES (?,?,?,?)";

        try {
            int count = jdbcTemplate.update(sql, userId, firstName, lastName, dob);
            userInfoCreated = (count == 1);
        } catch (DataAccessException e) {
            // userCreated remains false
        }
        return userInfoCreated;

    }

    @Override
    public boolean addNewFamily(String familyName, String username) {
        boolean familyCreated;
        boolean linkerCreated;
        String sqlFamily = "INSERT INTO family (family_name) VALUES (?)";
        String sqlLinker = "INSERT INTO user_family (family_id, user_id) "
                + "VALUES ((select family_id from family where family_name = ?), "
                + "(select user_id from users where username = ?))";

        int countFamily = jdbcTemplate.update(sqlFamily, familyName);
        int countLinker = jdbcTemplate.update(sqlLinker, familyName, username);

        familyCreated = (countFamily == 1);
        linkerCreated = (countLinker == 1);

        return familyCreated && linkerCreated;
    }

    public boolean addToFamily(String familyName, String username) {
        String sql = "INSERT INTO user_family (family_id, user_id) "
                + "VALUES ((select family_id from family where family_name = ?), "
                + "(select user_id from users where username = ?))";
        int userAddedToFamily = jdbcTemplate.update(sql, familyName, username);

        return userAddedToFamily == 1;
    }

    @Override
    public boolean familyNameExists(String familyName) {
        String sql = "SELECT 1 FROM family WHERE family_name = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, familyName);
        return rs.next();
    }

    @Override
    public List<UserInFamily> getFamilyMembers(String familyName) {
        List<UserInFamily> familyUsers = new ArrayList<>();
        String sql = "SELECT users.user_id, family.family_name, username, role, first_name, last_name, age FROM users JOIN user_family "
                + "ON users.user_id = user_family.user_id JOIN family ON user_family.family_id = family.family_id "
                + "WHERE family_name = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, familyName);
        while (rs.next()) {
            familyUsers.add(mapRowToFamilyUser(rs));
        }
        return familyUsers;
    }

    @Override
    public UserInFamily getFamilyMember(String username) {
        UserInFamily user = new UserInFamily();
        String sql = "SELECT users.user_id, family.family_name, username, role, first_name, last_name, age FROM users JOIN user_family "
                + "ON users.user_id = user_family.user_id JOIN family ON user_family.family_id = family.family_id "
                + "WHERE username = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, username);
        if (rs.next()) {
            user = mapRowToFamilyUser(rs);
        }
        return user;
    }

    private UserInFamily mapRowToFamilyUser(SqlRowSet rs) {
        UserInFamily user = new UserInFamily();
        user.setFamilyName(rs.getString("family_name"));
        user.setAge(rs.getString("age"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setRole(rs.getString("role"));
        user.setUsername(rs.getString("username"));
        user.setUser_id(rs.getInt("user_id"));
        user.setBooks(bookDAO.getUserBooks(user.getUser_id()));
        user.setFamilyPrizeDTO(prizeDAO.getFamilyPrizes(user.getFamilyName()));
        return user;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(rs.getString("role"));
        user.setActivated(true);
        return user;
    }

}
