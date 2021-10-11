package com.todo.dao;
//data access object. ToDoListApp의 데이터 구성.

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
	private String category;
    private String title;
    private String desc;
    private String due_date;
    private String current_date;
    private int Is_comp;


    public TodoItem(String category, String title, String desc,
    		String due_date){
        this.category=category;
    	this.title=title;
        this.desc=desc;
        this.due_date=due_date;
    }
    

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	
	public int getIs_comp() {
		return Is_comp;
	}

	public void setIs_comp(int Is_comp) {
		this.Is_comp = Is_comp;
	}
	
	@Override
	public String toString() {
		String todo = null;
		if(Is_comp == 0) {
			todo = id + " [" + category + "] " + title + "-" + desc + 
				"-" + due_date + "-" + current_date ;
		}
		else if(Is_comp == 1) {
			todo =  id + " [" + category + "] " + title + " [V] " + "-" + desc + 
					"-" + due_date + "-" + current_date;
		}
		return todo;
	}

}
