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
				+ "**���� ���� �۾�â�Դϴ�.**\n"
				+ "������ ������ �Է��ϼ���.\n");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� �����Դϴ�! �ٽ� �Է��ϼ���.");
			return;
		}
		
		sc.nextLine();
		//title �Է� �� ������ \n ���ڸ� �Ұ��ϱ� ���ؼ� �޾��ش�.
		System.out.println("������ ������ �Է��ϼ���.");
		desc = sc.nextLine().trim();
		
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String current_date = dateFormat.format(date);
		
		TodoItem t = new TodoItem(title, desc, current_date);
		list.addItem(t);
		//������ list ��ü�� t�� item�� �ϳ��� �־���. 
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.println("\n"
				+ "**���� ���� �۾�â�Դϴ�.**\n"
				+ "������ ������ ������ �Է��ϼ���.\n"
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
				+ "**���� ���� �۾�â�Դϴ�.**\n"
				+ "������Ʈ�� ������ ������ �Է��ϼ���.\n"
				+ "\n");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("�ش� ������ �������� �ʽ��ϴ�! �ٽ� �Է��ϼ���.");
			return;
		}

		System.out.println("�ش� ������ ���ο� ������ �Է��ϼ���.");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("�ߺ��� �����Դϴ�! �ٽ� �Է��ϼ���.");
			return;
		}
		
		sc.nextLine();
		System.out.println("�ش� ������ ���ο� ������ �Է��ϼ���. ");
		String new_description = sc.nextLine().trim();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String new_current_date = dateFormat.format(date);
        
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_current_date);
				l.addItem(t);
				System.out.println("�����մϴ�! ������ ������Ʈ �Ǿ����ϴ�:) ");
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
			//for(A:B); B�� ���̻� ���� ��ü�� ������ ���� B���� ��ü�� �ϳ��� ������ A�� �ִ´�.
				fw.write(item.toSaveString());
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("���� ������ ����Ǿ����ϴ�.");
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
