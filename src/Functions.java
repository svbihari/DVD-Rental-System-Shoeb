import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.text.DecimalFormat;

/*
 * Functions.java
 * @Project DVD Rental System
 * @Company MCS
 * @Developed by SHOEBKHAN BIHARI, CHIRAG GOSALIA, MIHIR PATEL
 * 
 * */

/*
 * The Function.java class is responsible for carrying out
 * the transaction methods for the DVD Rental System.
 * This contains methods for handling all the features of the DVD Rental System such as
 * login, logout, create, rent, buy, sell, add, and remove.  
 * In addition we have also included a method to generate a output log 
 * file of the DVD transaction summary.  It also contains some Boolean attributes 
 * for the purpose of verifying the login session type.  
 * 
 * */
class Functions {

	/*
	 * Hash Map Data structures user to store current DVD file It include
	 * Quantity, Price, Status for each DVD Title.
	 */
	static HashMap<String, String> temp_mapDVDstatus = new HashMap();
	static HashMap<String, Integer> temp_mapDVDquantity = new HashMap();
	static HashMap<String, Double> temp_mapDVDprice = new HashMap();

	static HashMap<String, String> mapDVDstatus = new HashMap();
	static HashMap<String, Integer> mapDVDquantity = new HashMap();
	static HashMap<String, Double> mapDVDprice = new HashMap();

	static Scanner in = new Scanner(System.in); // For input purposes

	// boolean for verifying which mode user has logged in
	private static boolean admin = false;
	private static boolean standard = false;

	// initialised writer to out.
	private static BufferedWriter out;

	public Functions() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Create method for create transactions, create is privileged transaction
	 * only allowed in admin mode Takes two user inputs DVD title and number of
	 * quantity checks for input validity and performs transaction. Save
	 * transaction onto DVD transaction summary file created for this session.
	 */
	public static void create() {

		// TODO Auto-generated method stub

		// Takes user input DVD title
		// variable initialised
		String title;
		System.out.println("Enter DVD title:");
		title = in.nextLine();
		title = title.toLowerCase();

		// Character length Check: for new DVD name is limited to at most 25
		// characters
		if (title.length() > 25 || title.length() <= 0) {
			System.out.println("Invalid Title. Transaction Terminated");
			return;
		}

		// Duplication Check: new DVD name must be different from all other
		// current DVDs
		if (mapDVDstatus.containsKey(title)) {
			System.out
					.println("Error: DVD with this name already exist. Transaciton Terminated");
			return;
		}

		// Takes user input number of copies
		// Variable initialised
		int quantity = 0;

		// number of copies has to be integer number
		try {
			System.out.println("Enter number of copies:");
			quantity = in.nextInt();

		} // end try

		catch (InputMismatchException inputMismatchException) {
			System.err.printf("\nException: %s\n", inputMismatchException);
			in.nextLine(); // discard input so user can try again
			System.out
					.println("You must enter integers. Tansaction Terminated\n");
			return;
		}

		// Check quantity for: number of copies can be at most 999
		if (quantity <= 0 || quantity > 999) {
			System.out.println("Error: Invalid number of copies");
			return;
		}

		// Create DVD with Title and quantity and save's onto temporary location
		// to satisfy following constrains:
		// copies of this DVD should not be available for rental in this
		// session.
		temp_mapDVDstatus.put(title, "R");
		temp_mapDVDquantity.put(title, quantity);

		System.out.println("Transaction successful");

		// Writing transaction to the file
		try {

			// Calls log function for recording transaction on to DVD
			// transaction summary file.
			log(03, title, quantity, "R", 000.00);

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// To clear Scanner
		in.nextLine();
	}

	/*
	 * login method for login transactions, it instates DVD transaction file
	 * with Date and time Ask for session type and directs to particular session
	 * entered by user, and allows user to logout without entering into
	 * Particular session.
	 */

	public static void login() {
		// TODO Auto-generated method stub

		// takes current date adn time for DVD transaction summary file name
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		String filename = dateFormat.format(cal.getTime());
		FileWriter newfile = null;
		try {
			newfile = new FileWriter(filename + ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out = new BufferedWriter(newfile);
		System.out.println("Login Accepted");

		boolean argument = true;

		// loop runs until user enters particular session type or logs out from
		// the system.
		while (argument) {

			// User input
			System.out.println("Enter session type: ");
			Scanner in = new Scanner(System.in);
			String input = in.next();

			// to admin mode
			if (input.equalsIgnoreCase("admin")) {
				// terminated loop
				argument = false;
				// Sets admin to true
				admin = true;
				// calls admin method from Session class
				Session.admin();

				// to standard mode
			} else if (input.equalsIgnoreCase("standard")) {
				// terminate loop
				argument = false;
				// Sets admin to true
				standard = true;
				// call standard method from Session class
				Session.standard();

			}

			// To logout without proceeding any further
			else if (input.equalsIgnoreCase("logout")) {
				// terminates loop
				argument = false;
				// calls logout method from Function class
				Functions.logout();

			}

			// Any other input string different from above prompts error and
			// loop continues.
			else {
				System.out.println("Invalid command");

			}
		}

	}

	/*
	 * logout method ends a Front End session. Method does following: writes out
	 * the DVD transaction file and stops accepting any transactions except
	 * login copies all data from temporary hash map to main one. returns to
	 * main method.
	 */
	public static void logout() {

		// TODO Auto-generated method stub
		// closing output file.
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Putting data from temporary to main storage.
		mapDVDstatus.putAll(temp_mapDVDstatus);
		mapDVDquantity.putAll(temp_mapDVDquantity);
		mapDVDprice.putAll(temp_mapDVDprice);

		// Clearing temporary
		temp_mapDVDstatus.clear();
		temp_mapDVDquantity.clear();
		temp_mapDVDprice.clear();

		// Setting login to be false means no session is running
		SystemFile.login = false;

		// Setting admin and standard to be false
		admin = false;
		standard = false;

		// Confirmation message.
		System.out.println("logging out");

	}

	public static void rent() {
		// TODO Auto-generated method stub

		// Checks for DVD availability

		if (mapDVDstatus.isEmpty()) {
			System.out.println("No DVD available for rent");

			try {

				log(01, "-", 0, "-", 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {

			// Takes user input DVD title
			// variable initialised
			String title;
			System.out.println("Enter DVD title:");
			title = in.nextLine();
			title = title.toLowerCase();

			// Constrain Check: DVD title must be a current DVD title with
			// enough copies available
			// Transaction terminates if condition is met
			if (!mapDVDstatus.containsKey(title)
					|| !mapDVDstatus.get(title).equalsIgnoreCase("r")) {
				System.out
						.println("Error: DVD with this name does not exist. Transaction Terminated");
				return;
			}

			// Takes user input number of copies
			// Variable initialised
			int quantity = 0;
			try {
				System.out.println("Enter number of copies:");
				quantity = in.nextInt();

			} // end try
			catch (InputMismatchException inputMismatchException) {
				System.err.printf("\nException: %s\n", inputMismatchException);
				in.nextLine(); // discard input so user can try again
				System.out
						.println("You must enter integers. Tansaction Terminated\n");
				return;
			}

			// Constrain Check: at most 3 copies of the same title can be rented
			// in one rent.
			if (quantity <= 0 || quantity > 3
					|| quantity > mapDVDquantity.get(title)) {
				System.out
						.println("Error: Invalid number of copies. Transaction Terminate");
				return;
			}

			System.out.println("Transaction successful");
			// Writing transaction to the file
			try {

				// Calls log function for recording transaction on to DVD
				// transaction summary file.
				log(01, title, quantity, mapDVDstatus.get(title), 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Clear Scanner
			in.nextLine();
		}
	}

	/*
	 * remove is privileged transaction requires admin session, this method
	 * cancels any outstanding rental requests from this session and delete the
	 * DVDFirst this method checks whether list is empty or not. Then it takes
	 * user input DVD title andCheck title with respect to constrains. If
	 * constrains are met it removes title form the list.
	 */

	public static void remove() {
		// TODO Auto-generated method stub

		// Checks for DVD availability if list is empty then there is nothing to
		// remove
		if (mapDVDstatus.isEmpty()) {
			System.out.println("No DVD available to remove");

			try {
				// write to DVD transaction summary file
				log(05, "-", 0, "-", 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			// Takes user input DVD title
			// variable initialised
			System.out.println("Enter DVD title:");
			String title = in.nextLine();
			title = title.toLowerCase();

			// Constrain Check: DVD title must be a current DVD title
			if (!mapDVDstatus.containsKey(title)) {

				System.out
						.println("Error: DVD with this name does not exist. Transaction Terminated");
				return;
			}
			// If it contains log the details
			if (mapDVDstatus.containsKey(title)) {

				try {

					log(05, title, mapDVDquantity.get(title), "-", 000.00);

				} catch (IOException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Removes title from storage
				mapDVDstatus.remove(title);
				mapDVDprice.remove(title);
				mapDVDquantity.remove(title);
				System.out.println(title + " removed");

			}
		}

	}

	/*
	 * Sell is privileged transaction requires admin session, this method Takes
	 * user input DVD title and price. it change the status of a DVD title from
	 * (R) to (S). Saves the transactions onto DVD transaction file.
	 */

	public static void sell() {
		// TODO Auto-generated method stub

		// Takes user input DVD title, price
		// variable initialised
		String title;
		double price = 0;
		System.out.println("Enter DVD title:");
		title = in.nextLine();
		title = title.toLowerCase();

		// Constrain Check: DVD title must be a current DVD title with
		// rental status
		// Transaction terminates if condition is met
		if (!mapDVDstatus.containsKey(title)
				|| !mapDVDstatus.get(title).equalsIgnoreCase("r")) {
			System.out
					.println("Error: DVD with this name does not exist. Transaction Terminated");
			return;
		}

		// Takes the price
		try {
			System.out.println("Enter the price:");
			price = in.nextDouble();

		} // end try
		catch (InputMismatchException inputMismatchException) {
			System.err.printf("\nException: %s\n", inputMismatchException);
			in.nextLine(); // discard input so user can try again
			System.out
					.println("You must enter integer or double. Tansaction Terminated\n");
			return;
		}

		// Constrain Check: the maximum price for a DVD sale is 150.00
		if (price > 150 || price < 0) {

			System.out.println("Error: Invalid price");
			return;
		}

		// Update status
		temp_mapDVDstatus.put(title, "s");
		temp_mapDVDprice.put(title, price);

		System.out.println("Transaction successful");
		// Writing transaction to the file
		try {

			log(07, title, mapDVDquantity.get(title),
					temp_mapDVDstatus.get(title), price);

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in.nextLine();

	}

	/*
	 * buy allows user to buy copies of a DVD, This method takes user input DVD
	 * title and number of copies in addition this method test for condition for
	 * privilege in purchasing number of copies. Saves the transaction on to DVD
	 * transaction summary file.
	 */
	public static void buy() {
		// TODO Auto-generated method stub

		// Checks for DVD availability
		if (mapDVDstatus.isEmpty()) {
			System.out.println("No DVD available for sale");

			// Write to DVD transaction summary file
			try {

				log(01, "-", 0, "-", 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {

			// Takes user input DVD title
			// variable initialised
			String title;
			System.out.println("Enter DVD title:");
			title = in.nextLine();
			title = title.toLowerCase();

			// Constrain Check: DVD title must be a current DVD title with
			// status "S"
			if (!mapDVDstatus.containsKey(title)
					|| !mapDVDstatus.get(title).equalsIgnoreCase("s")) {
				System.out.println("Error: DVD with this name does not exist");
			}

			// Take user input number of copies
			// Initialise variable
			int quantity = 0;

			// take integer value
			try {
				System.out.println("Enter number of copies:");
				quantity = in.nextInt();

			} // end try
			catch (InputMismatchException inputMismatchException) {
				System.err.printf("\nException: %s\n", inputMismatchException);
				in.nextLine(); // discard input so user can try again
				System.out
						.println("You must enter integers. Tansaction Terminated\n");
				return;
			}

			// Constrain Check: all available number of DVD copies can be
			// processed in admin mode
			if (admin) {

				if (quantity <= 0 || quantity > mapDVDquantity.get(title)) {
					System.out
							.println("Error: Invalid number of copies. Transaction Terminated");
					return;
				}
			}
			// Constrain Check: at most 5 copies can be purchased in one buy
			// transaction
			else if (standard) {

				if (quantity <= 0 || quantity > 5
						|| quantity > mapDVDquantity.get(title)) {
					System.out
							.println("Error: Invalid number of copies. Transaction Terminated");
					return;
				}
			}

			// display the cost per unit and the total cost to the user
			System.out.println("Cost per DVD is " + mapDVDprice.get(title)
					+ " and total cost is "
					+ (mapDVDprice.get(title) * quantity)
					+ " do you wish to proceed?");

			String confirmation = in.nextLine();

			// ask for confirmation in the form of yes or no.
			if (confirmation.equalsIgnoreCase("yes")) {
				int q = (mapDVDquantity.get(title) - quantity);
				mapDVDquantity.put(title, q);
				System.out.println("transaction successful");
			}

			else if (confirmation.equalsIgnoreCase("no")) {
				System.out.println("transaction unsuccessful");
				return;
			}

			// Write to transaction summary file
			try {

				log(06, title, quantity, mapDVDstatus.get(title),
						(mapDVDprice.get(title) * quantity));

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Clear Scanner
			in.nextLine();
		}
	}

	/*
	 * Sell is privileged transaction requires admin session, this method Takes
	 * user input DVD title and quantity. Check for input validity and add
	 * number of copies to existing DVD title
	 */

	public static void add() {

		// Takes user input DVD title
		// variable initialised
		String title;
		System.out.println("Enter DVD title:");
		title = in.nextLine();
		title = title.toLowerCase();

		// Constrain Check: DVD name must be the name of a current DVD available
		// for rent
		if (!mapDVDstatus.containsKey(title)
				|| (!mapDVDstatus.get(title).equalsIgnoreCase("s"))) {
			System.out
					.println("Error: DVD with this name does not exist. Transaction Terminated");
			return;
		}

		// Take number of copies;
		// initialise variable
		int quantity = 0;
		try // read two numbers and calculate quotient
		{
			System.out.println("Enter number of copies:");
			quantity = in.nextInt();

		} // end try
		catch (InputMismatchException inputMismatchException) {
			System.err.printf("\nException: %s\n", inputMismatchException);
			in.nextLine(); // discard input so user can try again
			System.out
					.println("You must enter integers. Tansaction Terminated\n");
			return;
		}

		// Constrain Check: added and initial quantity should not exceed 999.
		if (quantity <= 0 || (quantity + (mapDVDquantity.get(title))) > 999) {
			System.out.println("Error: Invalid number of copies");
			return;
		}

		int x = (quantity + (mapDVDquantity.get(title)));

		temp_mapDVDquantity.put(title, x);

		// write transaction to the DVD transaction summary file
		try {

			log(04, title, x, "R", 000.00);

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Clear Scanner
		in.nextLine();
	}

	/*
	 * Return transaction can be performed in any session. return a previously
	 * rented copy of a DVD. Method take user input DVD title and quantity,
	 * Checks for DVD title exist in system and number of quantity should be
	 * less then 3. Saves transaction information to the DVD transaction summary
	 * file.
	 */

	public static void reTurn() {
		// TODO Auto-generated method stub

		// Checks for DVD availability
		if (mapDVDstatus.isEmpty()) {
			System.out.println("No DVD requested for return");

			// write to DVD transaction summary file
			try {

				log(02, "-", 0, "-", 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			// Takes user input DVD title
			// variable initialised
			String title;

			System.out.println("Enter DVD title:");
			title = in.nextLine();
			title = title.toLowerCase();

			// Constrain check: If DVD is not in least prompt message
			if (!mapDVDstatus.containsKey(title)) {
				System.out
						.println("Error: DVD with this name does not exist. Transaction Terminated");
				return;
			}

			// Take user input number of copies
			// Initialise variable

			int quantity = 0;

			try {
				System.out.println("Enter number of copies:");
				quantity = in.nextInt();

			} // end try
			catch (InputMismatchException inputMismatchException) {
				System.err.printf("\nException: %s\n", inputMismatchException);
				in.nextLine(); // discard input so user can try again
				System.out
						.println("You must enter integers. Tansaction Terminated\n");
				return;
			}

			// Constrain check: at most 3 copies can be returned in one return
			// transaction
			if (quantity <= 0 || quantity > 3) {
				System.out
						.println("Error: Invalid number of copies. Transaction Terminated");
				return;
			}

			// update list
			mapDVDquantity.put(title, (mapDVDquantity.get(title) + quantity));
			System.out.println("transaction successful");

			// write to the DVD transaction summary file
			try {
				// call log method to write
				log(02, title, quantity, mapDVDstatus.get(title), 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Clear Scanner
			in.nextLine();
		}

	}

	/*
	 * log method is to format transaction log file. method formats DVD title ,
	 * transaction code, number of copies, status and price and it out writes to
	 * DVD transaction file created during front end session start.
	 */

	public static void log(int i, String title, int quantity, String string,
			double d) throws IOException {
		// TODO Auto-generated method stub
		// formating
		String dvd_title = String.format("%-25s", title);
		String transaction_code = String.format("%02d", i);
		String number_of_copies = String.format("%03d", quantity);
		String status = String.format("%-1s", string).toUpperCase();
		DecimalFormat price = new DecimalFormat("000.00");
		String dvd_price = price.format(d);
		// Outputting
		out.write('\n' + transaction_code + " " + dvd_title + " "
				+ number_of_copies + " " + status + " " + dvd_price);
		out.newLine();

	}

}
