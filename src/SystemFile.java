import java.util.Scanner;


public class SystemFile {

public static boolean login = false;
public static boolean exit = false;
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		
		while(!exit){
		Scanner in = new Scanner (System.in);
		System.out.println("Enter Command:");
		String input = in.next();
		
		
		if(input.equalsIgnoreCase("login")&&(!login)){
			login = true;
			Functions.login();
		}
		
		else if(input.equalsIgnoreCase("logout") && (login)){
			
			Functions.logout();
			login = false;
		}
		else if (input.equalsIgnoreCase("cancel")){
			
			
			System.out.println("Are you sure you want to cancel?");
			String response =  in.next();
				if (response.equalsIgnoreCase("yes")){
					Functions.cancel();
				}
				else{
					return;
				}	
		}
		
		else if (input.equalsIgnoreCase("rent")|| input.equalsIgnoreCase("sell")||
				input.equalsIgnoreCase("buy")|| input.equalsIgnoreCase("remove")|| 
				input.equalsIgnoreCase("return")|| input.equalsIgnoreCase("add")|| 
				input.equalsIgnoreCase("create")){
			
			System.out.println("Invalid command - Not logged in");
		}
		else if (input.equalsIgnoreCase("exit")){
			
			if (login){
				System.out.println("Error: You must log out before exiting system");
			}
			else exit = true;
		}
		
		else {
	
		System.out.println("Invalid Command");
	
	}
		}
	}
}
