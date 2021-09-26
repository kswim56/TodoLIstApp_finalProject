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
import com.todo.menu.Menu;

public class TodoUtil {
	
	private static final boolean String = false;
	public static int count = 0;
	
	public static void createItem(TodoList list) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**일정 생성 작업창입니다.**\n"
				+ "일정의 카테고리를 입력하세요.");
		
		category = sc.next();
		
		System.out.println("\n일정의 제목을 입력하세요.");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목입니다! 다시 입력하세요.");
			return;
		}
		
		sc.nextLine();
		//title 입력 후 들어오는 \n 문자를 소거하기 위해서 받아준다.
		System.out.println("\n일정의 내용을 입력하세요.");
		desc = sc.nextLine().trim();
		
		System.out.println("\n일정의 마감일자를 적어주세요. YY/MM/DD 형식으로 작성해 주세요. ");
		due_date = sc.next();
		
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String current_date = dateFormat.format(date);
		
		TodoItem t = new TodoItem(category, title, desc, due_date,  current_date);
		list.addItem(t);
		
		count += 1;
		//가져온 list 객체에 t의 item을 하나씩 넣어줌. 
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("\n**일정 삭제 작업창입니다.**"
				+ "\n삭제할 일정의 번호를 입력하세요.\n");

		
		int num = sc.nextInt();
		int i = 1;
		for (TodoItem item : l.getList()) {
			if (i == num) {
				l.deleteItem(item);
				count -= 1;
				break;
			}
			i ++ ;
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**일정 수정 작업창입니다.**\n"
				+ "업데이트할 일정의 번호을 입력하세요.\n"
				+ "\n");
		int num = sc.nextInt();
		int i = 1 ;
		for (TodoItem item : l.getList()) {
			i ++;
		}
		if (num > i ) {
			System.out.println("잘못된 번호입니다. 다시 입력하세요");
			return;
		}


		System.out.println("해당 일정의 새로운 카테고리를 입력하세요.");
		String new_category = sc.next();
		
		
		System.out.println("해당 일정의 새로운 제목을 입력하세요.");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 제목입니다! 다시 입력하세요.");
			return;
		}
		
		sc.nextLine();
		System.out.println("해당 일정의 새로운 내용을 입력하세요. ");
		String new_description = sc.nextLine().trim();
		
		
		System.out.println("해당 일정의 새로운 마감기한을 입력하세요. ");
		String new_due_date = sc.next();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String new_current_date = dateFormat.format(date);
        
        int j = 1;
        for (TodoItem item : l.getList()) {
			if (j == num) {
				l.deleteItem(item);
				TodoItem t = new TodoItem( new_category, new_title, new_description, new_due_date , new_current_date);
				l.addItem(t);
				System.out.println("축하합니다! 일정이 업데이트 되었습니다:) ");
			}
			j ++ ;
		}

	}

	
	public static void listAll(TodoList l) {
		System.out.println("[현재 " + count + "개의 일정이 있습니다. 확인하고 기한 전에 끝냅시다.]");
		int i = 1;
		for (TodoItem item : l.getList()) {
			System.out.println(i + ".\t[" + item.getCategory() + "] " 
			+  item.getTitle() + " - " + item.getDesc() + " - " 
			+ item.getDue_date() + " - " + item.getCurrent_date());
			i +=1;
		}
	}
	
	
	
	
	public static void findItem(TodoList l, String findword) {
		String keyword = findword;
		int kwd_num = 0;
		
		for (TodoItem item : l.getList()) {
			if (item.getCategory().contains(keyword)) {
				System.out.println((l.indexOf(item)+1) +  ".\t[" + item.getCategory() + "] " 
				+  item.getTitle() + " - " + item.getDesc() + " - " 
				+ item.getDue_date() + " - " + item.getCurrent_date());
				kwd_num += 1;
			}
			else if (item.getTitle().contains(keyword)) {
				System.out.println((l.indexOf(item)+1) +  ".\t[" + item.getCategory() + "] " 
				+  item.getTitle() + " - " + item.getDesc() + " - " 
				+ item.getDue_date() + " - " + item.getCurrent_date());
				kwd_num += 1;
			}
			else if (item.getDesc().contains(keyword)) {
				System.out.println((l.indexOf(item)+1) +  ".\t[" + item.getCategory() + "] " 
			    +  item.getTitle() + " - " + item.getDesc() + " - " 
				+ item.getDue_date() + " - " + item.getCurrent_date());
				kwd_num += 1;
			}
		}
		System.out.println("해당 키워드가 포함된 일정은 총 " + kwd_num + " 개 입니다.");
	}
	
	
	
	public static void findCate(TodoList l, String cate) {
		String key_cate = cate;
		int cate_num = 0;
		for(TodoItem item : l.getList()) {
			if (item.getCategory().contains(key_cate)) {
				System.out.println((l.indexOf(item)+1) +  ".\t[" + item.getCategory() + "] " 
						+  item.getTitle() + " - " + item.getDesc() + " - " 
						+ item.getDue_date() + " - " + item.getCurrent_date());
						cate_num += 1;
			}
		}
		System.out.println("해당 카테고리의 일정은 총 " + cate_num + " 개 입니다.");
	}
	
	
	public static void lsCate(TodoList l) {
		HashSet<String> cateHs = new HashSet<String>();
		for(TodoItem item : l.getList()) {
			cateHs.add(item.getCategory());
		}
		int i = cateHs.size();
		Iterator <String> iter = cateHs.iterator();
		int j = 1;
		while (iter.hasNext() == true) {
			String cateItem = iter.next();
			System.out.print( cateItem);
			if(j < i) {
				System.out.print(" / ");
			}
			j++;
		}
		System.out.println("\n총 " + i + "개의 카테고리가 등록되어 잇습니다.");
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
}
