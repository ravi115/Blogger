package com.blog.sqldatabase;

import java.sql.*;
import org.json.JSONObject;
import com.blog.dto.StudentInformation;
import com.blog.elastic.ElsticSearch;

public class StudentDatabase {

	private final static String DATABASE = "jdbc:mysql://localhost:3306/studentinfo";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "root";
	private final static String DRIVERNAME = "com.mysql.cj.jdbc.Driver";

	private Connection conn = null;
	private Statement stmt = null;
	StudentInformation sInfo = null;

	public StudentDatabase(StudentInformation sInfo) {
		this.sInfo = sInfo;
		initializeConnection();
	}

	private void initializeConnection() {
		try {
			// initializing driver
			Class.forName(DRIVERNAME);
			// connecting with database server
			conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
			// call method to insertQuery
			insertQuery(sInfo);
			// get result from sql
			getResult();
		} catch (ClassNotFoundException e) {
			System.out.println("error  " + e);
		} catch (SQLException e) {
			System.out.println("error  " + e);
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}

	}
	ElsticSearch eSearch = new ElsticSearch();
	private void getResult() {
		String sqlQuery = "select * from studentdetail";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);

			JSONObject jsonString = new JSONObject();
			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					String columnName = rs.getMetaData().getColumnName(i);
					String columnValue = rs.getString(columnName);
					jsonString.put(columnName, columnValue);
				}
				//call elastic search   for every tuple create index
				eSearch.createDocumet(jsonString);
			}
			System.out.println("the json string is : " + jsonString.toString());
		} catch (SQLException e) {
			System.out.println("error " + e);
		}
	}

	private void insertQuery(StudentInformation sInfo) {
		try {
			// values which has to insert in database
			String insertData = "\"" + sInfo.name + "\"" + "," + sInfo.id + "," + "\"" + sInfo.college + "\"" + ","
					+ "\"" + sInfo.email + "\"";
			String sqlInsert = "insert into studentdetail values(" + insertData + ")";
			stmt = conn.createStatement();
			stmt.executeLargeUpdate(sqlInsert);
		} catch (SQLException e) {
			System.out.println("error " + e);
		}
	}
}
