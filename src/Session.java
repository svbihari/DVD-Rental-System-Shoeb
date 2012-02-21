import java.util.Scanner;


public class Session {

	public Session() {
		// TODO Auto-generated constructor stub
	}

	public static void admin(){
		// TODO Auto-generated method stub
		System.out.println("Welcome to Admin Mode");
		boolean argument = true;
			
		/* keep looping to get inputs from user */
		while (argument){
			System.out.println("Enter transaction you wish to proceed:");
			Scanner in = new Scanner (System.in);
			String input = in.next();
			in.nextLine();
			if (input.equalsIgnoreCase("rent")){
				Functions.rent();
				argument = false;
				admin();
			}
			else if(input.equalsIgnoreCase("buy")){		
				
				Functions.buy();
				admin();
				argument = false;
				
			}
			else if(input.equalsIgnoreCase("sell")){		
				
				Functions.sell();
				
				admin();
				argument = false;
			}
			else if(input.equalsIgnoreCase("return")){		
				
				Functions.reTurn();
				argument = false;
				admin();
			}
			else if(input.equalsIgnoreCase("create")){		
				
				Functions.create();
				argument = false;
				admin();
			}
			else if(input.equalsIgnoreCase("add")){		
				
				Functions.add();
				argument = false;
				admin();
			}
			else if(input.equalsIgnoreCase("remove")){		
				
				Functions.remove();
				argument = false;
				admin();
			}
			else if(input.equalsIgnoreCase("logout")){		
				
				argument = false;
				Functions.logout();
			}
			else if (input.equalsIgnoreCase("cancel")){
				
				
				System.out.println("Are you sure you want to cancel?");
				String response =  in.nextLine();
				
					if (response.equalsIgnoreCase("yes")){
						argument = false;
						Functions.cancel();
					}
					else if(response.equalsIgnoreCase("no")){
						
					}
					
					else{
						System.out.println("Invalid arguement please enter yes/no");
					}
			}
			
			else {
				
				System.out.println("Invalid command");
				
			}
		}
		
	}

	public static void standard() {
		// TODO Auto-generated method stub
		
		System.out.println("Welcome to Admin Standard");
		boolean argument = true;
			
		while (argument){
			System.out.println("Enter transaction you wish to proceed:");
			Scanner in = new Scanner (System.in);
			String input = in.next();
			
			if (input.equalsIgnoreCase("rent")){
				
				Functions.rent();
				argument = false;
				standard();
			}
			else if(input.equalsIgnoreCase("buy")){		
				
				Functions.buy();
				argument = false;
				standard();
			}
			
			else if(input.equalsIgnoreCase("return")){		
				
				Functions.reTurn();
				argument = false;
				standard();
			}
			
			
			else if(input.equalsIgnoreCase("logout")){		
				
				argument = false;
				Functions.logout();
			}
			else if (input.equalsIgnoreCase("cancel")){
				
				
				System.out.println("Are you sure you want to cancel?");
				String response =  in.next();
					if (response.equalsIgnoreCase("yes")){
						argument = false;
						Functions.cancel();
					}
					else{
						return;
					}	
			}
			
			else {
				
				System.out.println("Invalid command");
				argument = false;
				standard();
			}
		}
		
	}

}