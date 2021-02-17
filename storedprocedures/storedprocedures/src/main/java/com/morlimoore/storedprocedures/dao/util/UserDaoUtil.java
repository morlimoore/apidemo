package com.morlimoore.storedprocedures.dao.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morlimoore.storedprocedures.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoUtil implements RowMapper<User> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        return user;
    }

    public MapSqlParameterSource getClientPropertyMapSqlParameterSource(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        int age = user.getAge();
        String email = user.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        params.addValue("age",age);
        params.addValue("email", email);
        params.addValue("first_name", firstName);
        params.addValue("last_name", lastName);

        return params;
    }
}