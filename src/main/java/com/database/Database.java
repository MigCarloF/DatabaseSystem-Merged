package com.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database{

	public static final Database database = new Database();

	private ArrayList<Fee> fees = new ArrayList<>();
	private ArrayList<Bus> bus_accounts = new ArrayList<>();
	private ArrayList<Employee> employee_accounts = new ArrayList<>();
	private Scanner sc = new Scanner(System.in);

	private void clearScreen() throws IOException, InterruptedException{
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}

	// adds the fee transaction 'f' to the list of fees
	public void addFee(Fee f){
		fees.add(f);
	}

	// adds the employee account 'e' to the list of employee accounts
	public void addEmployee(Employee e){
		employee_accounts.add(e);
	}

	// adds the bus account 'b' to the list of bus accounts
	public void addBus(Bus b){
		bus_accounts.add(b);
	}

	// returns an arraylist of all the fee transactions
	public ArrayList<Fee> getAllFees(){
		return fees;
	}

	// returns an arraylist of all the bus accounts
	public ArrayList<Bus> getAllBuses(){
		return bus_accounts;
	}

	// returns an arraylist of all the employee accounts
	public ArrayList<Employee> getAllEmployees(){
		return employee_accounts;
	}

	/************************para sa mga naniniwala na "CMD is life!"***************************/
	// displays all the fees in a table
	private void displayFeeTable(){
		System.out.println("\n\n\t\t\t\t     [Table of Fees]\n");
		if(fees.size() == 0 || fees == null){
			System.out.println("\t\t\tNo fee transaction found!");
		}else{
			System.out.println("   |   OR #   |  Date    |   Time   |   Bus Number   |   Cashier");
			int counter = 1;
			for(Fee f : fees){
				System.out.println("--------------------------------------------------------------------------------------------");
				System.out.println(" " + counter + " | " + f.getOrNum() + "   |   " + f.getDatePaid() + "  | " + f.getTimePaid() + " |    " + getBusByPlate(f.getBus_plate()).getBusNumber() + "     | " + f.getEmployeeID());
				counter++;
			}
		}
	}

	// displays all the bus accounts in a table
	private void displayBusTable(){
		System.out.println("\n\n\t\t\t\t     [Bus Accounts]\n");
		if(bus_accounts.size() == 0 || bus_accounts == null){
			System.out.println("\t\t\tNo bus account found!");
		}else{
			System.out.println("   |   Company   |   Plate #   |  Bus Type  |  Bus Number");
			int counter = 1;
			for(Bus b : bus_accounts){
				String bus_type = "";
				if(b.isMiniBus()){
					bus_type = "minibus";
				}else{
					bus_type = "regular";
				}

				System.out.println("------------------------------------------------------------");
				System.out.println(" " + counter + " |    " + b.getCompany() + "    |   " + b.getPlateNo() + "    |   " + bus_type + "  | " + b.getBusNumber());

				counter++;
			}
		}
	}

	// displays all the employee accounts in a table
	private void displayEmployeeTable(){
		System.out.println("\n\n\t\t\t\t     [Employee Accounts]\n");
		if(employee_accounts.size() == 0 || employee_accounts == null){
			System.out.println("\t\t\tNo employee account found!");
		}else{
			System.out.println("   |         Name          |     ID     |    Position  ");
			int counter = 1;
			for(Employee e : employee_accounts){
				System.out.println("------------------------------------------------------------");
				System.out.println(" " + counter + " |  " + e.getName() + "    |   " + e.getID() + "    |   " + e.getPosition());

				counter++;
			}	
		}
	}

	// called by outside functions to display the fee
	public void displayFees() throws IOException, InterruptedException{
		clearScreen();
		displayFeeTable();
	}

	// called by outside functions to display the bus accounts
	public void displayBusAccounts() throws IOException, InterruptedException{
		clearScreen();
		displayBusTable();
	}

	// called by outside functions to display the employee accounts
	public void displayEmployeeAccounts() throws IOException, InterruptedException{
		clearScreen();
		displayEmployeeTable();
	}

	// called by outside functions to display all the fee transactions, employee accounts and bus accounts
	public void displayAll() throws IOException, InterruptedException{
		clearScreen();
		displayFeeTable();
		displayBusTable();
		displayEmployeeTable();
	}
	/******************************************************************************************/

	/***********************make sure to check in testing if arraylist is null*********************************/
	// returns the bus with the plate number equal to the balue of the parameter passed unto the function
	public Bus getBusByPlate(String plate){
		for(Bus b : bus_accounts){
			if(b.getPlateNo().equals(plate)){
				return b;
			}
		}
		return null;
	}

	// returns the bus with the company and bus number equal to the values of the perimeter given respectively
	public Bus getBusByCompanyAndNumber(String company, String busNum){
		for(Bus b : bus_accounts){
			if(b.getCompany().equals(company) && b.getBusNumber().equals(busNum)){
				return b;
			}
		}
		return null;
	}

	// returns the fee transaction with the OR number equal to the value passed unto the function
	public Fee getFeeByORNum(String orNum){
		for(Fee f : fees){
			if(f.getOrNum().equals(orNum)){
				return f;
			}
		}
		return null;
	}

	/***********************make sure to check in testing if arraylist is null*********************************/
	public ArrayList<Bus> getBus(ArrayList<String> description, ArrayList<String> values){
		ArrayList<ArrayList<Bus>> found = new ArrayList<>();
		for(int i = 0; i < description.size(); i++){
			found.add(getBus(description.get(i), values.get(i)));
		}

		ArrayList<Bus> final_ = found.get(0); 
		if(found.size() > 1){
			final_ = found.get(0);
			for(int i = 1; i < found.size(); i++){
				ArrayList<Bus> current = found.get(i);
				for(int j = 0; j < final_.size(); j++){
					Boolean here = false;
					for(int l = 0; l < current.size(); l++){
						if(final_.get(j).getPlateNo().equals(current.get(l).getPlateNo())){
							here = true;
							break;
						}
					}
					if(!here){
						final_.remove(j);
					}
				}
			}
		}

		return final_;
	}

	public ArrayList<Fee> getFee(ArrayList<String> description, ArrayList<String> values){
		ArrayList<ArrayList<Fee>> found = new ArrayList<>();
		for(int i = 0; i < description.size(); i++){
			found.add(getFee(description.get(i), values.get(i)));
		}

		ArrayList<Fee> final_ = found.get(0); 
		if(found.size() > 1){
			final_ = found.get(0);
			for(int i = 1; i < found.size(); i++){
				ArrayList<Fee> current = found.get(i);
				for(int j = 0; j < final_.size(); j++){
					Boolean here = false;
					for(int l = 0; l < current.size(); l++){
						if(final_.get(j).getOrNum().equals(current.get(l).getOrNum())){
							here = true;
							break;
						}
					}
					if(!here){
						final_.remove(j);
					}
				}
			}
		}

		return final_;
	}

	public ArrayList<Employee> getEmployee(ArrayList<String> description, ArrayList<String> values){
		ArrayList<ArrayList<Employee>> found = new ArrayList<>();
		for(int i = 0; i < description.size(); i++){
			found.add(getEmployee(description.get(i), values.get(i)));
		}

		ArrayList<Employee> final_ = found.get(0); 
		if(found.size() > 1){
			final_ = found.get(0);
			for(int i = 1; i < found.size(); i++){
				ArrayList<Employee> current = found.get(i);
				for(int j = 0; j < final_.size(); j++){
					Boolean here = false;
					for(int l = 0; l < current.size(); l++){
						if(final_.get(j).getID().equals(current.get(l).getID())){
							here = true;
							break;
						}
					}
					if(!here){
						final_.remove(j);
					}
				}
			}
		}

		return final_;
	}

	public ArrayList<Fee> getFee(String description, String value){ // descriptions: feeType, feeAmount, etc.
		ArrayList<Fee> found = new ArrayList<>();
		switch(description){
			case "timePaid":
				for(Fee f : fees){
					if(f.getTimePaid().equals(value)){
						found.add(f);
					}
				}
				break;
			case "datePaid":
				for(Fee f : fees){
					if(f.getDatePaid().equals(value)){
						found.add(f);
					}
				}
				break;
			case "orNum":
				for(Fee f : fees){
					if(f.getOrNum().equals(value)){
						found.add(f);
					}
				}
				break;
			case "bus_plate":
				for(Fee f : fees){
					if(f.getBus_plate().equals(value)){
						found.add(f);
					}
				}
				break;
			case "employeeID":
				for(Fee f : fees){
					if(f.getEmployeeID().equals(value)){
						found.add(f);
					}
				}
				break;
		}
		return found;
	}

	public ArrayList<Bus> getBus(String description, String value){ // descriptions: plateNo, company, isMinibus and busNumber
		ArrayList<Bus> found = new ArrayList<>();
		
		if(description.equals("plateNo")){
			for(Bus b : bus_accounts){
				if(b.getPlateNo().equals(value)){
					found.add(b);
				}
			}
		}else if(description.equals("company")){
			for(Bus b : bus_accounts){
				if(b.getCompany().equals(value)){
					found.add(b);
				}
			}
		}else if(description.equals("isMinibus")){
			for(Bus b : bus_accounts){
				if(b.isMiniBus() && value.equals("true")){
					found.add(b);
				}else if ((!b.isMiniBus() && value.equals("false"))){
					found.add(b);
				}
			}
		}else if(description.equals("busNumber")){
			for(Bus b : bus_accounts){
					if(b.getBusNumber().equals(value)){
						found.add(b);
					}
				}
			}
			else
			System.out.println("Wrong Input for Database getBus");
		return found;
	} 

	public ArrayList<Employee> getEmployee(String description, String value){ // descriptions: name, id, position
		ArrayList<Employee> found = new ArrayList<>();
		switch(description){
			case "name":
				for(Employee e : employee_accounts){
					if(e.getName().equals(value)){
						found.add(e);
					}
				}
				break;
			case "id":
				for(Employee e : employee_accounts){
					if(e.getID().equals(value)){
						found.add(e);
					}
				}
				break;
			case "position":
				for(Employee e : employee_accounts){
					if(e.getPosition().equals(value)){
						found.add(e);
					}
				}
				break;
		}
		return found;
	}
	/**********************************************************************************************************/

	/*************deleting/removing stuffs********************/
	// deletes the fee transactin in which OR number is equal to the value passed
	public void removeFee(String or_num) throws IOException, InterruptedException{
		int i = 0;
		for(Fee f : fees){
			if(f.getOrNum().equals(or_num)){
				fees.remove(i);
				break;
			}
			i++;
		}
	}

	// deletes the employee account in which the employee ID is equal to the value passed
	public void removeEmployee(String emp_id){
		int i = 0;
		for(Employee e : employee_accounts){
			if(e.getID().equals(emp_id)){
				employee_accounts.remove(i);
				break;
			}
			i++;
		}
	}

	// deletes the bus account in which the plate number is equal to the value passed
	public void removeBus(String bus_plate){
		int i = 0;
		for(Bus b : bus_accounts){
			if(b.getPlateNo().equals(bus_plate)){
				bus_accounts.remove(i);
				break;
			}
			i++;
		}
	}
	/***********************************************/
}
