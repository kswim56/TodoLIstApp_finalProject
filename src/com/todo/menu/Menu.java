package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("TodoListApp�� �޴��Դϴ�.");
        System.out.println("1. ������ �߰��մϴ�. ( add )");
        System.out.println("2. ������ �����մϴ�. ( del )");
        System.out.println("3. ������ �����մϴ�.  ( edit )");
        System.out.println("4. ��� ������ �����ݴϴ�. ( ls )");
        System.out.println("5. �Է��� Ű���尡 �ִ� �������� ã���ݴϴ�. (find)");
        System.out.println("6. �Է��� ī�װ��� �������� ã���ݴϴ� (find_cate)");
        System.out.println("6. ������ �̸� ������ �����մϴ�. ( ls_name_asc )");
        System.out.println("7. ������ �̸� �������� �����մϴ�. ( ls_name_desc )");
        System.out.println("8. ������ ��¥������ �����մϴ�. ( ls_date )");
        System.out.println("9. ī�װ� ����� �����մϴ�. (ls_cate)");
        System.out.println("10. ������ ��¥ �������� �����մϴ�. (ls_date_desc)");
        System.out.println("11. �����մϴ�. (exit or press escape key to exit)");
    }
    
    public static void prompt()
    {
    	System.out.println("\n���ϴ� �޴��� �Է��ϼ���. > ");
    }
}
