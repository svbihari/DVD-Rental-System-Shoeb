import java.util.Scanner;

/*
 * Session.java
 * @Project DVD Rental System
 * @Company MCS
 * @Developed by SHOEBKHAN BIHARI, CHIRAG GOSALIA, MIHIR PATEL
 * 
 * */

/*
 *This class has two methods which include admin and standard. The two methods of
 *standard and admin inherit methods from functions class depending on their respective
 *privileges.   
 * */

public class Session {

	public Session() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * admin method with all feature of DVD Rental System
	 */
	public static void admin() {
		// TODO Auto-generated method stub
		
		boolean argument = true;

		/* keep looping to get inputs from user */
		while (argument) {

			// User input
			System.out.println("Enter transaction you wish to proceed:");
			Scanner in = new Scanner(System.in);
			String input = in.next();
			in.nextLine();

			// condition for rent function.
			if (input.equalsIgnoreCase("rent")) {
				Functions.rent(); // calls rent from Funcions class
				argument = false;
				admin(); // returns to admin menu after completing transaction

				// condition for buy function.
			} else if (input.equalsIgnoreCase("buy")) {

				Functions.buy(); // calls buy from Functions class
				admin(); // returns to admin menu after completing transaction
				argument = false;

				// condition for sell function.
			} else if (input.equalsIgnoreCase("sell")) {

				Functions.sell(); // calls sell Functions class
				admin();// returns to admin menu after completing transaction
				argument = false;

				// condition for return function.
			} else if (input.equalsIgnoreCase("return")) {

				Functions.reTurn(); // calls return from Functions class
				argument = false;
				admin(); // returns to admin menu after completing transaction

				// Condition for create functions
			} else if (input.equalsIgnoreCase("create")) {

				Functions.create(); // calls create from Functions class
				argument = false;
				admin(); // return to admin menu after completing transaction

				// condition for add function
			} else if (input.equalsIgnoreCase("add")) {

				Functions.add(); // calls add from Functions class
				argument = false;
				admin(); // return to admin menu after completing transaction

				// Condition for remove functions
			} else if (input.equalsIgnoreCase("remove")) {

				Functions.remove(); // call remove from Functions class
				argument = false;
				admin(); // return to admin menu after completing transaction

				// Condition for logout functions
			} else if (input.equalsIgnoreCase("logout")) {

				argument = false;
				Functions.logout(); // call logout from Functions class
			}

			else {

				System.out.println("Invalid command");

			}
		}

	}

	/*
	 * Standard method with privileged feature of DVD Rental System
	 */

	public static void standard() {
		// TODO Auto-generated method stub

		
		boolean argument = true;

		while (argument) {

			// Takes user input
			System.out.println("Enter transaction you wish to proceed:");
			Scanner in = new Scanner(System.in);
			String input = in.next();

			// condition for rent function
			if (input.equalsIgnoreCase("rent")) {

				Functions.rent(); // calls rent from Functions class
				argument = false;
				standard(); // return to standard mode after completing
							// transaction

				// condition for buy function
			} else if (input.equalsIgnoreCase("buy")) {

				Functions.buy(); // calls buy from Functions class
				argument = false;
				standard(); // return to standard mode after completing
							// transaction
			}
			// condition for return function
			else if (input.equalsIgnoreCase("return")) {

				Functions.reTurn(); // calls return from Functions class
				argument = false;
				standard(); // return to standard mode after completing
							// transaction
			}

			// condition for logout function
			else if (input.equalsIgnoreCase("logout")) {

				argument = false;
				Functions.logout(); // calls logout from Functions class
			}

			else {
				// prompt error if any other string except above
				System.out.println("Invalid command");
				argument = false;
				standard();
			}
		}

	}

}