package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("TodoListApp의 메뉴입니다.");
        System.out.println("1. 일정 추가 ( add )");
        System.out.println("2. 일정 삭제 ( del )");
        System.out.println("3. 일정 수정 ( edit )");
        System.out.println("4. 모든 일정 나열 ( ls )");
        System.out.println("5. 이번주의 일정 나열 (ls_week)");
        System.out.println("6. 특정 키워드를 포함하는 일정 나열 (find)");
        System.out.println("7. 특정 카테고리의 일정 나열 (find_cate)");
        System.out.println("8. 이름순 정렬 ( ls_name )");
        System.out.println("9. 이름 역순 정렬 ( ls_name_desc )");
        System.out.println("10. 날짜순 정렬 ( ls_date )");
        System.out.println("11. 날짜 역순 정렬 (ls_date_desc)");
        System.out.println("12. 카테고리 목록 나열 (ls_cate)");
        System.out.println("13. 일정 완료 (comp)");
       	System.out.println("14. 완료한 일정 목록 나열(ls_comp)");
        System.out.println("15. 종료 (exit)");
    }
    
    public static void prompt()
    {
    	System.out.println("\n원하는 메뉴를 입력하세요. > ");
    }
}
