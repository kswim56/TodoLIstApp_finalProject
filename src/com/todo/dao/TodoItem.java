package com.todo.dao;
//data access object. ToDoListApp의 데이터 구성.


public class TodoItem {
	private String category;
    private String title;
    private String desc;
    private String due_date;
    private String current_date;


    public TodoItem(String category, String title, String desc,
    		String due_date, String current_date){
        this.category=category;
    	this.title=title;
        this.desc=desc;
        this.due_date=due_date;
        this.current_date= current_date;
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

	public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
}
