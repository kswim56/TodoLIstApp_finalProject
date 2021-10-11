package com.todo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

		private static Connection conn = null;
		
		public static void closeConnection() {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static Connection getConnection() {
			if (conn == null) {
				try {
					//SQLite JDBC üũ
					Class.forName("org.sqlite.JDBC");
					
					//SQLite �����ͺ��̽� ���Ͽ� ����
					conn = DriverManager.getConnection("jdbc:sqlite:" + "todolist.db");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return conn;
		}

}