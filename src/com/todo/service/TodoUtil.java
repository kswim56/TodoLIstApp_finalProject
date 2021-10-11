package com.todo.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;

public class TodoUtil {
	
	private static final boolean String = false;
	
	public static void createItem(TodoList l) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**일정 생성 작업창입니다.**\n"
				+ "일정의 카테고리를 입력하세요.");
		category = sc.next();
		
		System.out.println("\n일정의 제목을 입력하세요.");
		title = sc.next();
		if (l.isDuplicate(title)) {
			System.out.printf("중복된 제목입니다! 다시 입력하세요.");
			return;
		}
		
		sc.nextLine();
		//title 입력 후 들어오는 \n 문자를 소거하기 위해서 받아준다.
		
		System.out.println("\n일정의 내용을 입력하세요.");
		desc = sc.nextLine().trim();
		
		System.out.println("\n일정의 마감일자를 적어주세요. YY/MM/DD 형식으로 작성해 주세요. ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		if(l.addItem(t)>0)
			System.out.println("일정이 추가되었습니다.");
		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n**일정 삭제 작업창입니다.**"
				+ "\n삭제할 일정의 번호를 입력하세요.\n");

		int index = sc.nextInt();
		if (l.deleteItem(index) > 0)
			System.out.println("일정이 삭제되었습니다.");
	}


	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**일정 수정 작업창입니다.**\n"
				+ "업데이트할 일정의 번호을 입력하세요.");
		int index = sc.nextInt();

		System.out.println("\n새로운 카테고리를 입력하세요.");
		new_category = sc.next();
		
		System.out.println("\n새로운 제목을 입력하세요.");
		new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("\n중복된 제목입니다! 다시 입력하세요.");
			return;
		}
		
		sc.nextLine();
		System.out.println("\n새로운 내용을 입력하세요. ");
		new_desc = sc.nextLine().trim();
		
		System.out.println("\n새로운 마감기한을 입력하세요. ");
		new_due_date = sc.next();

		TodoItem t = new TodoItem( new_category, new_title, new_desc, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("\n일정이 업데이트 되었습니다!");

	}
	
	public static void listAll(TodoList l) {
		//그냥 list를 내보내는 method
			System.out.println("[현재 " + l.getCount() + "개의 일정이 있습니다. 확인하고 기한 전에 끝냅시다.]");
			for (TodoItem item : l.getList()) {
				System.out.println(item.toString());
			}
		}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
	//sort 해서 list를 내보내는 method
		System.out.println("[현재 " + l.getCount() + "개의 일정이 있습니다. 확인하고 기한 전에 끝냅시다.]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void completed(TodoList l, String comp_num) {
		System.out.println( l.getList(comp_num));
		System.out.println("해당 일정을 완료 처리 하였습니다. \n힘내서 다음 일정도 완료합시다.");
	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count ++;
		}
		System.out.println("해당 키워드가 포함된 일정은 총 " + count + " 개 입니다.");
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count ++;
		}
		System.out.println("해당 카테고리의 일정은 총 " + count + " 개 입니다.");
	}
	
	
	public static void lsCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.println(item + " ");
			count ++;
		}
		System.out.println("\n총 " + count + "개의 카테고리가 등록되어 잇습니다.");
	}
	

	/*
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
		count = 0;
		System.out.println("일정 정보가 저장되었습니다.");
	}
	
	public static void loadList(TodoList l, String TodoList) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("TodoList.txt"));
			
			String onelist;
			while((onelist = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(onelist, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem t = new TodoItem(category, title, desc, due_date, current_date);
				l.addItem(t);
				count += 1;
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
	
	*/
}
