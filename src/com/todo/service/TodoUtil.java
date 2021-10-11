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
				+ "**���� ���� �۾�â�Դϴ�.**\n"
				+ "������ ī�װ��� �Է��ϼ���.");
		category = sc.next();
		
		System.out.println("\n������ ������ �Է��ϼ���.");
		title = sc.next();
		if (l.isDuplicate(title)) {
			System.out.printf("�ߺ��� �����Դϴ�! �ٽ� �Է��ϼ���.");
			return;
		}
		
		sc.nextLine();
		//title �Է� �� ������ \n ���ڸ� �Ұ��ϱ� ���ؼ� �޾��ش�.
		
		System.out.println("\n������ ������ �Է��ϼ���.");
		desc = sc.nextLine().trim();
		
		System.out.println("\n������ �������ڸ� �����ּ���. YY/MM/DD �������� �ۼ��� �ּ���. ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		if(l.addItem(t)>0)
			System.out.println("������ �߰��Ǿ����ϴ�.");
		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n**���� ���� �۾�â�Դϴ�.**"
				+ "\n������ ������ ��ȣ�� �Է��ϼ���.\n");

		int index = sc.nextInt();
		if (l.deleteItem(index) > 0)
			System.out.println("������ �����Ǿ����ϴ�.");
	}


	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**���� ���� �۾�â�Դϴ�.**\n"
				+ "������Ʈ�� ������ ��ȣ�� �Է��ϼ���.");
		int index = sc.nextInt();

		System.out.println("\n���ο� ī�װ��� �Է��ϼ���.");
		new_category = sc.next();
		
		System.out.println("\n���ο� ������ �Է��ϼ���.");
		new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("\n�ߺ��� �����Դϴ�! �ٽ� �Է��ϼ���.");
			return;
		}
		
		sc.nextLine();
		System.out.println("\n���ο� ������ �Է��ϼ���. ");
		new_desc = sc.nextLine().trim();
		
		System.out.println("\n���ο� ���������� �Է��ϼ���. ");
		new_due_date = sc.next();

		TodoItem t = new TodoItem( new_category, new_title, new_desc, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("\n������ ������Ʈ �Ǿ����ϴ�!");

	}
	
	public static void listAll(TodoList l) {
		//�׳� list�� �������� method
			System.out.println("[���� " + l.getCount() + "���� ������ �ֽ��ϴ�. Ȯ���ϰ� ���� ���� �����ô�.]");
			for (TodoItem item : l.getList()) {
				System.out.println(item.toString());
			}
		}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
	//sort �ؼ� list�� �������� method
		System.out.println("[���� " + l.getCount() + "���� ������ �ֽ��ϴ�. Ȯ���ϰ� ���� ���� �����ô�.]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void completed(TodoList l, String comp_num) {
		System.out.println( l.getList(comp_num));
		System.out.println("�ش� ������ �Ϸ� ó�� �Ͽ����ϴ�. \n������ ���� ������ �Ϸ��սô�.");
	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count ++;
		}
		System.out.println("�ش� Ű���尡 ���Ե� ������ �� " + count + " �� �Դϴ�.");
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count ++;
		}
		System.out.println("�ش� ī�װ��� ������ �� " + count + " �� �Դϴ�.");
	}
	
	
	public static void lsCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.println(item + " ");
			count ++;
		}
		System.out.println("\n�� " + count + "���� ī�װ��� ��ϵǾ� �ս��ϴ�.");
	}
	

	/*
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
	
	*/
}
