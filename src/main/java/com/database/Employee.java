package com.database;

public class Employee{
	private String name, id, position;

	public Employee(String name, String id, String position){
		this.name = name;
		this.id = id;
		this.position = position;
	}

	public String getName(){
		return name;
	}

	public String getID(){
		return id;
	}

	public String getPosition(){
		return position;
	}
}