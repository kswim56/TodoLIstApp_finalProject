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
				+ "**���� ���� �۾�â�Դϴ�.**\n"
				+ "������ ī�װ��� �Է��ϼ���.");
		
		category = sc.next();
		
		System.out.println("\n������ ������ �Է��ϼ���.");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� �����Դϴ�! �ٽ� �Է��ϼ���.");
			return;
		}
		
		sc.nextLine();
		//title �Է� �� ������ \n ���ڸ� �Ұ��ϱ� ���ؼ� �޾��ش�.
		System.out.println("\n������ ������ �Է��ϼ���.");
		desc = sc.nextLine().trim();
		
		System.out.println("\n������ �������ڸ� �����ּ���. YY/MM/DD �������� �ۼ��� �ּ���. ");
		due_date = sc.next();
		
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String current_date = dateFormat.format(date);
		
		TodoItem t = new TodoItem(category, title, desc, due_date,  current_date);
		list.addItem(t);
		
		count += 1;
		//������ list ��ü�� t�� item�� �ϳ��� �־���. 
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("\n**���� ���� �۾�â�Դϴ�.**"
				+ "\n������ ������ ��ȣ�� �Է��ϼ���.\n");

		
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
				+ "**���� ���� �۾�â�Դϴ�.**\n"
				+ "������Ʈ�� ������ ��ȣ�� �Է��ϼ���.\n"
				+ "\n");
		int num = sc.nextInt();
		int i = 1 ;
		for (TodoItem item : l.getList()) {
			i ++;
		}
		if (num > i ) {
			System.out.println("�߸��� ��ȣ�Դϴ�. �ٽ� �Է��ϼ���");
			return;
		}


		System.out.println("�ش� ������ ���ο� ī�װ��� �Է��ϼ���.");
		String new_category = sc.next();
		
		
		System.out.println("�ش� ������ ���ο� ������ �Է��ϼ���.");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("�ߺ��� �����Դϴ�! �ٽ� �Է��ϼ���.");
			return;
		}
		
		sc.nextLine();
		System.out.println("�ش� ������ ���ο� ������ �Է��ϼ���. ");
		String new_description = sc.nextLine().trim();
		
		
		System.out.println("�ش� ������ ���ο� ���������� �Է��ϼ���. ");
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
				System.out.println("�����մϴ�! ������ ������Ʈ �Ǿ����ϴ�:) ");
			}
			j ++ ;
		}

	}

	
	public static void listAll(TodoList l) {
		System.out.println("[���� " + count + "���� ������ �ֽ��ϴ�. Ȯ���ϰ� ���� ���� �����ô�.]");
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
		System.out.println("�ش� Ű���尡 ���Ե� ������ �� " + kwd_num + " �� �Դϴ�.");
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
		System.out.println("�ش� ī�װ��� ������ �� " + cate_num + " �� �Դϴ�.");
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
		System.out.println("\n�� " + i + "���� ī�װ��� ��ϵǾ� �ս��ϴ�.");
	}
	
	
	public static void saveList(TodoList l, String TodoList) {
		FileWriter fw;
		try {
			fw = new FileWriter("TodoList.txt");
			
			for(TodoItem item : l.getList()) {
			//for(A:B); B�� ���̻� ���� ��ü�� ������ ���� B���� ��ü�� �ϳ��� ������ A�� �ִ´�.
				fw.write(item.toSaveString());
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count = 0;
		System.out.println("���� ������ ����Ǿ����ϴ�.");
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
			System.out.println("����� ���� ����� �ҷ��Խ��ϴ�. \n� Ȯ���ϰ� ���� ���������� ������ �ѵ����� �˽ô�.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
}
