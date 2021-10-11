package com.todo;

import java.util.Scanner;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.DbConnect;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l�̶�� �̸��� TodoLIst ��ü�� �ϳ� ����� 
		//����ٰ� ����Ʈ �ȿ� ����ִ� ������� �ٷ�� ���� l�� ����Ѵ�.
		//l.importdata("TodoList.txt");
		boolean isList = false;
		boolean quit = false;
		
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
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
			
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;

			case "ls_name":
				System.out.println("�̸������� ������ ���� ����Դϴ�.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("�̸� �������� ������ ���� ����Դϴ�.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
				
			case "ls_date":
				System.out.println("��¥������ ������ ���� ����Դϴ�.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("��¥ �������� ������ ���� ����Դϴ�.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "ls_cate":
				TodoUtil.lsCateAll(l);
				break;
			
			case "comp" :
				String comp_num = sc.nextLine().trim();
				TodoUtil.completed(l, comp_num);
				break;
			
			case "ls_comp":
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
			
			// if(isList) l.listAll();
		} while (!quit);
		//TodoUtil.saveList(l, "TodoList");
	}
}
