package com.itbulls.learnit.onlinestore.persistence.utils.connectionpools;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DbcpConnectionPool {

	private static BasicDataSource ds = new BasicDataSource();

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		ds.setUrl("jdbc:mysql://localhost:3306/learn_it_db");
		ds.setUsername("michal");
		ds.setPassword("michal");
		ds.setMinIdle(3);
		ds.setTimeBetweenEvictionRunsMillis(1000);
		ds.setMaxIdle(20);
		ds.setMaxOpenPreparedStatements(200);
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
}
