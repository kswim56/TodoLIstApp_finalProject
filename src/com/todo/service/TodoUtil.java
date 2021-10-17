package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	private static final boolean String = false;
	
	public static void createItem(TodoList l) {
		
		String category, title, desc, place, due_date;
		int importance;
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
		
		System.out.println("\n������ ����Ǵ� ��Ҹ� �Է��ϼ���.");
		place = sc.nextLine().trim();
		
		System.out.println("\n������ �������ڸ� �����ּ���. YY/MM/DD �������� �ۼ��� �ּ���. ");
		due_date = sc.next();
		
		System.out.println("\n������ �߿䵵�� �Է��ϼ���.\n[1:�ſ� �߿� / 2:�߿� / 3:�� �߿� / 0:�߿䵵 ����]");
		importance = sc.nextInt();
		
		TodoItem t = new TodoItem(category, title, desc, place, due_date, importance);
		if(l.addItem(t)>0)
			System.out.println("������ �߰��Ǿ����ϴ�.\n");
		
		loadFs();
	}

	public static void deleteItem(TodoList l, String del_nums) {
		Scanner sc = new Scanner(System.in);
		String[] del_num = del_nums.split(" ");
		int count = 0;
		for(String d : del_num) {
			if (l.deleteItem(d) > 0) {
				count ++;
			}
		}
		System.out.println("\n�� " + count + "���� ������ �����Ͽ����ϴ�.");
	}


	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_place, new_due_date;
		int new_importance;
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
		
		System.out.println("\n���ο� ��Ҹ� �Է��ϼ���. ");
		new_place = sc.nextLine().trim();
		
		System.out.println("\n���ο� ���������� �Է��ϼ���. ");
		new_due_date = sc.next();
		
		sc.nextLine();
		System.out.println("\n���ο� �߿䵵�� �Է��ϼ���.");
		new_importance = sc.nextInt();

		TodoItem t = new TodoItem( new_category, new_title, new_desc, new_place, new_due_date, new_importance);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("\n������ ������Ʈ �Ǿ����ϴ�!\n");
		loadFs();
	}
	
	public static void listAll(TodoList l) {
		//�׳� list�� �������� method
			System.out.println("\n[���� " + l.getCount() + "���� ������ �ֽ��ϴ�. Ȯ���ϰ� ���� ���� �����ô�.]");
			for (TodoItem item : l.getList()) {
				System.out.println(item.toString());
			}
			System.out.println("");
			loadFs();
		}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
	//sort �ؼ� list�� �������� method
		System.out.println("\n[���� " + l.getCount() + "���� ������ �ֽ��ϴ�. Ȯ���ϰ� ���� ���� �����ô�.]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void list_week(TodoList l) {
		System.out.println("\n�̹� ���� �����Դϴ�. �������� ������ ���� ���ڱ���!");
		ArrayList<String> today = new ArrayList<String>();
		ArrayList<String> tomr = new ArrayList<String>();
		ArrayList<String> twodl = new ArrayList<String>();
		ArrayList<String> threedl = new ArrayList<String>();
		ArrayList<String> fivedl = new ArrayList<String>();
		ArrayList<String> fourdl = new ArrayList<String>();
		ArrayList<String> sixdl = new ArrayList<String>();
		for (TodoItem item : l.getList()) {
			int dl = l.getDaysLeft(item);
			if(dl == 0) {
				today.add("<<"+ item.getTitle() + "  -  " + item.getDesc() +">>");
			}
			else if(dl == 1) {
				tomr.add("<<"+ item.getTitle() + "  -  " + item.getDesc() +">>");
			}
			else if(dl == 2) {
				twodl.add("<<"+ item.getTitle() + "  -  " + item.getDesc() +">>");
			}
			else if(dl == 3) {
				threedl.add("<<" + item.getTitle() + "  -  " + item.getDesc() +">>");
			}
			else if(dl == 4) {
				fourdl.add("<<"+ item.getTitle() + "  -  " + item.getDesc() +">>");
			}
			else if(dl == 5) {
				fivedl.add("<<"+ item.getTitle() + "  -  " + item.getDesc() +">>");
			}
			else if(dl == 6) {
				sixdl.add("<<"+ item.getTitle() + "  -  " + item.getDesc() +">>");
			}
		}
		System.out.println("\n===========================================Dashboard===========================================");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, E");
		System.out.println("\n" + dateFormat.format(cal.getTime()));
		System.out.println(today);
		cal.add(Calendar.DATE, 1);
		System.out.println("\n" + dateFormat.format(cal.getTime()));
		System.out.println(tomr);
		cal.add(Calendar.DATE, 1);
		System.out.println("\n" + dateFormat.format(cal.getTime()));
		System.out.println(twodl);
		cal.add(Calendar.DATE, 1);
		System.out.println("\n" + dateFormat.format(cal.getTime()));
		System.out.println(threedl);
		cal.add(Calendar.DATE, 1);
		System.out.println("\n" + dateFormat.format(cal.getTime()));
		System.out.println(fourdl);
		cal.add(Calendar.DATE, 1);
		System.out.println("\n" + dateFormat.format(cal.getTime()));
		System.out.println(fivedl);
		cal.add(Calendar.DATE, 1);
		System.out.println("\n" + dateFormat.format(cal.getTime()));
		System.out.println(sixdl);
		System.out.println("\n=============================================================================================");
	}
	
	public static void completed(TodoList l, String comp_nums) {
		String[] comp_num = comp_nums.split(" ");
		int count = 0;
		for(String c : comp_num) {
			if(l.completeItem(c) > 0) {
				count ++;
			}
		}
		System.out.println("\n�� " + count +"���� ������ �Ϸ� ó�� �Ͽ����ϴ�. \n������ ���� ������ �Ϸ��սô�.\n");
		loadFs();
	}
	
	public static void lsComp(TodoList l) {
		int count = 0;
		for (TodoItem item : l.getList()) {
			if(item.getIs_comp() == 1) {
				System.out.println(item);
				count ++;
			}
		}
		System.out.println("\n��" + count + "���� ������ �Ϸ��Ͽ����ϴ�. \n�� �ϰ� �ֽ��ϴ�.\n");
		loadFs();
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
	
	
	public static void loadFs() {
		ArrayList<String> fs = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("TodoList_famous_saying.txt"));
			String onelist;
			int i=0;
			while((onelist = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(onelist, "##");
				fs.add(i, st.nextToken());
			}
			br.close();
			int j = (int)((Math.random()*fs.size()*10)%10);
			System.out.println(fs.get(j));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
