package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;
	
	public TodoList() {
		this.conn = DbConnect.getConnection();
		//이 Connection type의 변수에 getConnection으로 값을 받아온다.
	}
	
	
	public void importdata(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("TodoList.txt"));
			String line;
			String sql = "insert into list (category, title, memo, due_date, current_date)"
							+ "values (?, ?, ?, ?, ?);";
			int records = 0;
			int count = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				//그냥 Statement와의 차이는, preparedstatement 는 sql의 ?표에다가 값을 넣는다.
				//print()와 printf()의 차이 정도..?
				pstmt.setString(1, category);
				pstmt.setString(2, title);
				pstmt.setString(3, description);
				pstmt.setString(4, due_date);
				pstmt.setString(5, current_date);
				count = pstmt.executeUpdate();
				if(count > 0) {
					records ++;
				}
				pstmt.close();
			}
			System.out.println(records + " 개의 기록들이 이전되었습니다!!");
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public int addItem(TodoItem t) {
		String sql = "insert into list (category, title, memo, place,  current_date, due_date, importance)"
						+ "values (?, ?, ?, ?, ?, ? ,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getCategory());
			pstmt.setString(2, t.getTitle());
			pstmt.setString(3, t.getDesc());
			pstmt.setString(4, t.getPlace());
			pstmt.setString(5, t.getCurrent_date());
			pstmt.setString(6, t.getDue_date());
			pstmt.setInt(7, t.getImportance());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}

	public int deleteItem(String del_num) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,del_num);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set  category=?, title=?, memo =?, place = ?, current_date=?, due_date=?, importance=?"
						+ "where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getCategory());
			pstmt.setString(2, t.getTitle());
			pstmt.setString(3, t.getDesc());
			pstmt.setString(4, t.getPlace());
			pstmt.setString(5, t.getCurrent_date());
			pstmt.setString(6, t.getDue_date());
			pstmt.setInt(7, t.getImportance());
			pstmt.setInt(8, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
	//그냥 ls 할때 list를 불러옴
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int importance = rs.getInt("importance");
				int Is_comp = rs.getInt("is_completed");
				TodoItem t = new TodoItem( category, title, description, place, due_date, importance);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_comp(Is_comp);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int importance = rs.getInt("importance");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(category, title, description, place, due_date, importance);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_comp(is_completed);
				list.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int importance = rs.getInt("importance");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(category, title, description, place, due_date, importance);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_comp(is_completed);
				list.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int getDaysLeft(TodoItem item) {
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		Date date = new Date();
		String today_date = dateFormat.format(date);
		int today = Integer.parseInt(today_date);
		
		String due_item = item.getDue_date();
		String due_sub = due_item.substring(0, 2) + due_item.substring(3, 5) + due_item.substring(6);
		int due_date = Integer.parseInt(due_sub);
		
		int daysLeft = due_date - today;
		
		return daysLeft;
	}
	
	public int getCount() {
		int count = 0;
		try {
			Statement stmt;
			count = 0;
			stmt = conn.createStatement();
			String sql = "SELECT count(id) From list";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int importance = rs.getInt("importance");
				TodoItem t = new TodoItem(category, title, description, place, due_date, importance);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	public int completeItem(String comp_num) {
		String sql = "update list set  is_completed = 1 where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comp_num);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public Boolean isDuplicate(String key_title) {
	      ArrayList<TodoItem> list = new ArrayList<TodoItem>();
	      PreparedStatement pstmt;
	      key_title = "%"+key_title+"%";
    	 try {
			String sql = "SELECT * FROM list WHERE title like ?";
			 pstmt =conn.prepareStatement(sql);
			 pstmt.setString(1,	 key_title);
			 ResultSet rs = pstmt.executeQuery();
			 if(rs.next()) {
				 return true;
			 }
			 else {
				 return false;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return true;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


}
