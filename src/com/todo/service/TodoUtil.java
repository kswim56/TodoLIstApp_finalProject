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
		
		System.out.println("\n일정이 시행되는 장소를 입력하세요.");
		place = sc.nextLine().trim();
		
		System.out.println("\n일정의 마감일자를 적어주세요. YY/MM/DD 형식으로 작성해 주세요. ");
		due_date = sc.next();
		
		System.out.println("\n일정의 중요도를 입력하세요.\n[1:매우 중요 / 2:중요 / 3:덜 중요 / 0:중요도 없음]");
		importance = sc.nextInt();
		
		TodoItem t = new TodoItem(category, title, desc, place, due_date, importance);
		if(l.addItem(t)>0)
			System.out.println("일정이 추가되었습니다.\n");
		
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
		System.out.println("\n총 " + count + "개의 일정을 삭제하였습니다.");
	}


	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_place, new_due_date;
		int new_importance;
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
		
		System.out.println("\n새로운 장소를 입력하세요. ");
		new_place = sc.nextLine().trim();
		
		System.out.println("\n새로운 마감기한을 입력하세요. ");
		new_due_date = sc.next();
		
		sc.nextLine();
		System.out.println("\n새로운 중요도를 입력하세요.");
		new_importance = sc.nextInt();

		TodoItem t = new TodoItem( new_category, new_title, new_desc, new_place, new_due_date, new_importance);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("\n일정이 업데이트 되었습니다!\n");
		loadFs();
	}
	
	public static void listAll(TodoList l) {
		//그냥 list를 내보내는 method
			System.out.println("\n[현재 " + l.getCount() + "개의 일정이 있습니다. 확인하고 기한 전에 끝냅시다.]");
			for (TodoItem item : l.getList()) {
				System.out.println(item.toString());
			}
			System.out.println("");
			loadFs();
		}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
	//sort 해서 list를 내보내는 method
		System.out.println("\n[현재 " + l.getCount() + "개의 일정이 있습니다. 확인하고 기한 전에 끝냅시다.]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void list_week(TodoList l) {
		System.out.println("\n이번 주의 일정입니다. 차근차근 열심히 끝내 보자구요!");
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
		System.out.println("\n총 " + count +"개의 일정을 완료 처리 하였습니다. \n힘내서 다음 일정도 완료합시다.\n");
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
		System.out.println("\n총" + count + "개의 일정을 완료하였습니다. \n잘 하고 있습니다.\n");
		loadFs();
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
