import java.util.Scanner;

/*
 * SystemFile.java
 * @Project DVD Rental System
 * @Company MCS
 * @Developed by SHOEBKHAN BIHARI, CHIRAG GOSALIA, MIHIR PATEL
 * 
 * */

/*
 * This class contains main method for front end DVD Rental system.
 * Main class asks for login information and initiates front end session.
 * */
public class SystemFile {


public static boolean login = false;  				//check for user login
public static boolean exit = false;					// to terminate system
	
/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

/*
 * while loop for command line input
 * 
 * */	
		while(!exit){
/*
 * Takes user input
 * */												
		Scanner in = new Scanner (System.in);        
		System.out.println("Enter Command:");
		String input = in.next();
		
		//Condition if user inputs login and there is no current session it logs in
	
		if(input.equalsIgnoreCase("login")&&(!login)){
			login = true;
			Functions.login();
		}
		
		// Condition for logout checks, user should be login in for logout
		
		else if(input.equalsIgnoreCase("logout") && (login)){
			
			Functions.logout();
			login = false;
		}
		
		// Condition for other input test, Does not permit following transaction prior to login
		
		else if (input.equalsIgnoreCase("rent")|| input.equalsIgnoreCase("sell")||
				input.equalsIgnoreCase("buy")|| input.equalsIgnoreCase("remove")|| 
				input.equalsIgnoreCase("return")|| input.equalsIgnoreCase("add")|| 
				input.equalsIgnoreCase("create")){
			
			System.out.println("Invalid command - Not logged in");
		}
		
		// Condition to terminate system 
		
		else if (input.equalsIgnoreCase("exit")){
			
			// IF user tries to terminate system without logging out
			// Exit takes place only if login is set to true meaning user must be logged in
			
			if (login){
				System.out.println("Error: You must log out before exiting system");
			}
		
			// Sets exit to true for loop termination 
			else exit = true;
		}
		
		// Condition for bad inputs or invalid commands
		else {
	
		System.out.println("Invalid Command");
	
	}
		}
	}
}
