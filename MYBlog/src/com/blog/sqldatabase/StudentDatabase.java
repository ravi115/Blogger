package com.blog.sqldatabase;

import java.sql.*;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class StudentDatabase {

	private static final Logger LOG = Logger.getLogger(StudentDatabase.class);
	private final static String DATABASE = "jdbc:mysql://localhost:3306/studentinfo";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "root";
	private final static String DRIVERNAME = "com.mysql.cj.jdbc.Driver";

	private Connection conn = null;
	private Statement stmt = null;

	public StudentDatabase() {
		initializeConnection();
	}

	private void initializeConnection() {
		LOG.debug("initialization of SQL server starts-");
		try {
			// initializing driver
			Class.forName(DRIVERNAME);
			// connecting with database server
			conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
			LOG.debug("connection is made with URL : " + DATABASE + " UserName : " + USERNAME + " Password : "
					+ PASSWORD);
			LOG.debug("connection is - :" + conn.toString());
		} catch (ClassNotFoundException e) {
			LOG.debug("error is : " + e);
		} catch (SQLException e) {
			LOG.debug("error is : " + e);
		}

	}

	public JSONObject getResult() {
		String sqlQuery = "select * from studentdetail";
		JSONObject jsonString = new JSONObject();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					String columnName = rs.getMetaData().getColumnName(i);
					String columnValue = rs.getString(columnName);
					jsonString.put(columnName, columnValue);
				}
			}
		} catch (SQLException e) {
			LOG.debug("error is : " + e);
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				LOG.debug("error is : " + e);
			}
		}
		return jsonString;
	}

	public void insertQuery(final int id, final String name, final String college, final String email) {
		try {
			// values which has to insert in database
			String insertData = "\"" + name + "\"" + "," + id + "," + "\"" + college + "\"" + "," + "\"" + email + "\"";
			String sqlInsert = "insert into studentdetail values(" + insertData + ")";
			LOG.debug("Query to insert data is : " + sqlInsert);
			stmt = conn.createStatement();
			stmt.executeLargeUpdate(sqlInsert);
		} catch (SQLException e) {
			LOG.debug("error is : " + e);
		}
	}
}
