package com.pravin.spring.secuity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pravin.spring.secuity.model.UserInfo;

public class UserInfoMapper implements RowMapper<UserInfo> {

    public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	String userName = rs.getString("Username");
	String password = rs.getString("Password");

	return new UserInfo(userName, password);
    }

}
