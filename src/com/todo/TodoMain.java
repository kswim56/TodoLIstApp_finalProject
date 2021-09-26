package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l�̶�� �̸��� TodoLIst ��ü�� �ϳ� ����� 
		//����ٰ� ����Ʈ �ȿ� ����ִ� ������� �ٷ�� ���� l�� ����Ѵ�.
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "TodoList");
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "find":
				String keyword = sc.next();
				TodoUtil.findItem(l, keyword);
				break;
			
			case "find_cate":
				String cate = sc.next();
				TodoUtil.findCate(l, cate);
				break;

			case "ls_name_asc":
				System.out.println("�̸������� ������ ���� ����Դϴ�.");
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				System.out.println("�̸� �������� ������ ���� ����Դϴ�.");
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
				
			case "ls_date":
				System.out.println("��¥������ ������ ���� ����Դϴ�.");
				l.sortByDate();
				isList = true;
				break;
				
			case "ls_date_desc":
				System.out.println("��¥ �������� ������ ���� ����Դϴ�.");
				l.sortByDate();
				l.reverseList();
				isList = true;
				break;
			
			case "ls_cate":
				TodoUtil.lsCate(l);
				break;
			
			
			case "exit":
				quit = true;
				System.out.println("�����մϴ�.");
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			default:
				System.out.println("�߸��� ��ɾ��Դϴ�! �޴��� ������ help�� �Է��ϼ���");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		TodoUtil.saveList(l, "TodoList");
	}
}
