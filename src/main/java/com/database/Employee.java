package com.database;

public class Employee{
	private String username, password;
	private String firstName, lastName;
	private String workType;
	private Boolean activeEmployee;

	public Employee(){}

	public Employee(Boolean activeEmployee, String firstName, String lastName, String password, String username, String workType) { //for firebase
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.workType = workType;
		this.activeEmployee = activeEmployee;
	}

	public Employee(String username, String password, String firstName, String lastName, String workType) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.workType = workType;
		activeEmployee = true;	// set default to true because I think you wont add an inactive employee
								// and this constructor is only called when you create a new employee
								//call setter to set it to inactive
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Boolean isActiveEmployee() {
		return activeEmployee;
	}

	public void setActiveEmployee(boolean activeEmployee) {
		this.activeEmployee = activeEmployee;
	}




}