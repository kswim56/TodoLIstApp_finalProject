package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("TodoListApp의 메뉴입니다.");
        System.out.println("1. 일정을 추가합니다. ( add )");
        System.out.println("2. 일정을 삭제합니다. ( del )");
        System.out.println("3. 일정을 수정합니다.  ( edit )");
        System.out.println("4. 모든 일정을 보여줍니다. ( ls )");
        System.out.println("5. 일정을 이름 순으로 정렬합니다. ( ls_name_asc )");
        System.out.println("6. 일정을 이름 역순으로 정렬합니다. ( ls_name_desc )");
        System.out.println("7. 일정을 날짜순으로 정렬합니다. ( ls_date )");
        System.out.println("8. 종료합니다. (exit or press escape key to exit)");
    }
    
    public static void prompt()
    {
    	System.out.println("\n원하는 메뉴를 입력하세요. > ");
    }
}
