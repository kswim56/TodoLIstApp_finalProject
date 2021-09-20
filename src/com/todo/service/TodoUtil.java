package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**일정 생성 작업창입니다.**\n"
				+ "일정의 제목을 입력하세요.\n");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목입니다! 다시 입력하세요.");
			return;
		}
		
		sc.nextLine();
		//title 입력 후 들어오는 \n 문자를 소거하기 위해서 받아준다.
		System.out.println("일정의 내용을 입력하세요.");
		desc = sc.nextLine().trim();
		
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String current_date = dateFormat.format(date);
		
		TodoItem t = new TodoItem(title, desc, current_date);
		list.addItem(t);
		//가져온 list 객체에 t의 item을 하나씩 넣어줌. 
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.println("\n"
				+ "**일정 삭제 작업창입니다.**\n"
				+ "삭제할 일정의 제목을 입력하세요.\n"
				+ "\n");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**일정 수정 작업창입니다.**\n"
				+ "업데이트할 일정의 제목을 입력하세요.\n"
				+ "\n");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("해당 일정이 존재하지 않습니다! 다시 입력하세요.");
			return;
		}

		System.out.println("해당 일정의 새로운 제목을 입력하세요.");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 제목입니다! 다시 입력하세요.");
			return;
		}
		
		sc.nextLine();
		System.out.println("해당 일정의 새로운 내용을 입력하세요. ");
		String new_description = sc.nextLine().trim();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String new_current_date = dateFormat.format(date);
        
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_current_date);
				l.addItem(t);
				System.out.println("축하합니다! 일정이 업데이트 되었습니다:) ");
			}
		}

	}

	
	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "] " + item.getDesc() + " - " + item.getCurrent_date());
		}
	}
	
	
	public static void saveList(TodoList l, String TodoList) {
		FileWriter fw;
		try {
			fw = new FileWriter("TodoList.txt");
			
			for(TodoItem item : l.getList()) {
			//for(A:B); B에 더이상 꺼낼 객체가 없을떄 까지 B에서 객체를 하나씩 꺼내서 A에 넣는다.
				fw.write(item.toSaveString());
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("일정 정보가 저장되었습니다.");
	}
	
	public static void loadList(TodoList l, String TodoList) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("TodoList.txt"));
			
			String onelist;
			while((onelist = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(onelist, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem t = new TodoItem(title, desc, current_date);
				l.addItem(t);
			}
			br.close();
			System.out.println("저장된 일정 목록을 불러왔습니다. \n어서 확인하고 빨리 끝내버리는 성실한 한동인이 됩시다.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
}
