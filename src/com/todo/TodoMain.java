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
		//l이라는 이름의 TodoLIst 객체를 하나 만들고 
		//여기다가 리스트 안에 들어있는 내용들을 다루기 위해 l을 사용한다.
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
				System.out.println("이름순으로 정렬한 일정 목록입니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("이름 역순으로 정렬한 일정 목록입니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
				
			case "ls_date":
				System.out.println("날짜순으로 정렬한 일정 목록입니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜 역순으로 정렬한 일정 목록입니다.");
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
				System.out.println("종료합니다.");
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			default:
				System.out.println("잘못된 명령어입니다! 메뉴를 보려면 help를 입력하세요");
				break;
			}
			
			// if(isList) l.listAll();
		} while (!quit);
		//TodoUtil.saveList(l, "TodoList");
	}
}
