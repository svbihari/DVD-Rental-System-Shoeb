import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

class Functions {

	static HashMap<String, String> mapDVDstatus = new HashMap();
	static HashMap<String, Integer> mapDVDquantity = new HashMap();
	static HashMap<String, String> mapDVDprice = new HashMap();

	static Scanner in = new Scanner(System.in);
	static boolean admin = false;
	static boolean standard = false;

	// 0 = rent and 1 = buy
	static BufferedWriter out;

	public Functions() {
		// TODO Auto-generated constructor stub
	}

	public static void create() {
		// TODO Auto-generated method stub
		String title;
		do {
			System.out.println("Enter DVD title:");
			title = in.nextLine();
			title = title.toLowerCase();

			if (title.length() > 25 || title.length() <= 0) {
				System.out.println("Invalid Title");
			}

			if (mapDVDstatus.containsKey(title)) {
				System.out.println("Error: DVD with this name already exist");
			}

		} while (title.length() > 25 || title.length() <= 0
				|| mapDVDstatus.containsKey(title)
				|| title.equalsIgnoreCase("cancel"));

		int quantity = 0;
		do {
			try {
				System.out.println("Enter number of copies:");
				quantity = in.nextInt();

			} // end try
			catch (InputMismatchException inputMismatchException) {
				System.err.printf("\nException: %s\n", inputMismatchException);
				in.nextLine(); // discard input so user can try again
				System.out
						.println("You must enter integers. Please try again.\n");
			}
			if (quantity <= 0 || quantity > 999) {
				System.out.println("Error: Invalid number of copies");
			}

		} while (quantity <= 0 || quantity > 999);

		mapDVDstatus.put(title, "R");
		mapDVDquantity.put(title, quantity);

		try {

			log(03, title, quantity, "R", 000.00);

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		in.nextLine();
	}

	public static void login() {
		// TODO Auto-generated method stub

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

		while (argument) {
			System.out.println("Enter session type: ");
			Scanner in = new Scanner(System.in);
			String input = in.next();

			if (input.equalsIgnoreCase("admin")) {

				argument = false;
				admin = true;
				Session.admin();

			} else if (input.equalsIgnoreCase("standard")) {

				argument = false;
				standard = true;
				Session.standard();

			}

			else if (input.equalsIgnoreCase("logout")) {

				argument = false;
				Functions.logout();

			}

			else if (input.equalsIgnoreCase("cancel")) {

				System.out.println("Are you sure you want to cancel?");
				String response = in.next();
				if (response.equalsIgnoreCase("yes")) {
					argument = false;
					Functions.cancel();
				} else {
					return;
				}
			}

			else {

				System.out.println("Invalid command");

			}
		}

	}

	public static void logout() {
		// TODO Auto-generated method stub
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SystemFile.login = false;
		admin = false;
		standard = false;
		System.out.println("logging out");

	}

	public static void rent() {
		// TODO Auto-generated method stub
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
			String title;
			do {
				System.out.println("Enter DVD title:");
				title = in.nextLine();
				title = title.toLowerCase();

				if (!mapDVDstatus.containsKey(title)
						|| !mapDVDstatus.get(title).equalsIgnoreCase("r")) {
					System.out
							.println("Error: DVD with this name does not exist");
				}

			} while (!mapDVDstatus.containsKey(title)
					|| !mapDVDstatus.get(title).equalsIgnoreCase("r"));

			int quantity = 0;
			do {
				try {
					System.out.println("Enter number of copies:");
					quantity = in.nextInt();

				} // end try
				catch (InputMismatchException inputMismatchException) {
					System.err.printf("\nException: %s\n",
							inputMismatchException);
					in.nextLine(); // discard input so user can try again
					System.out
							.println("You must enter integers. Please try again.\n");
				}

				if (admin) {

					if (quantity <= 0 || quantity > mapDVDquantity.get(title)) {
						System.out.println("Error: Invalid number of copies");
					}
				}

				else if (standard) {

					if (quantity <= 0 || quantity > 3
							|| quantity > mapDVDquantity.get(title)) {
						System.out.println("Error: Invalid number of copies");
					}
				}

			} while ((admin && (quantity <= 0 || quantity > mapDVDquantity
					.get(title)))
					|| (standard && (quantity <= 0 || quantity > 3)));

			mapDVDquantity.put(title, (mapDVDquantity.get(title) - quantity));
			System.out.println("transaction successful");

			try {

				log(01, title, quantity, mapDVDstatus.get(title), 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.nextLine();
		}
	}

	public static void remove() {
		// TODO Auto-generated method stub

		if (mapDVDstatus.isEmpty()) {
			System.out.println("No DVD available to remove");

			try {

				log(05, "-", 0, "-", 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Enter DVD title:");
			String title = in.nextLine();
			title = title.toLowerCase();

			if (!mapDVDstatus.containsKey(title)) {

				System.out.println("Error: DVD with this name does not exist");

			}

			if (mapDVDstatus.containsKey(title)) {

				try {

					log(05, title, mapDVDquantity.get(title), "-", 000.00);

				} catch (IOException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mapDVDstatus.remove(title);
				mapDVDprice.remove(title);
				mapDVDquantity.remove(title);
				System.out.println(title + " removed");

			}
		}

	}
	
	public static void sell() {
		// TODO Auto-generated method stub
	/*
		 * sell - change the status of a DVD title from rental (R) to sale (S) •
		 * should ask for the DVD title (as a text line) • should ask for a sale
		 * price for the DVD in dollars (e.g. 15.00) • should save this
		 * information to the DVD transaction file • Constraints: o privileged
		 * transaction - only accepted when logged in in admin mode o DVD title
		 * must be the name of a current DVD o the maximum price for a DVD sale
		 * is 150.00 o edit status only changes the status from rental to sale –
		 * it can not change the status back o no further transactions should be
		 * accepted on a DVD with a new status In this session
		 */
	
	
	
	
	}

	public static void buy() {
		// TODO Auto-generated method stub
	
	}

	

	public static void add() {

		String title;
		do {
			System.out.println("Enter DVD title:");
			title = in.nextLine();
			title = title.toLowerCase();

			if (title.length() > 25 || title.length() <= 0) {
				System.out.println("Invalid Title");
			}

			if (!mapDVDstatus.containsKey(title)) {
				System.out.println("Error: DVD with this name does not exist");
			}

		} while (title.length() > 25 || title.length() <= 0
				|| !mapDVDstatus.containsKey(title));

		int quantity = 0;
		do {
			try // read two numbers and calculate quotient
			{
				System.out.println("Enter number of copies:");
				quantity = in.nextInt();

			} // end try
			catch (InputMismatchException inputMismatchException) {
				System.err.printf("\nException: %s\n", inputMismatchException);
				in.nextLine(); // discard input so user can try again
				System.out
						.println("You must enter integers. Please try again.\n");
			}
			if (quantity <= 0 || (quantity + (mapDVDquantity.get(title))) > 999) {
				System.out.println("Error: Invalid number of copies");
			}

		} while (quantity <= 0
				|| (quantity + (mapDVDquantity.get(title))) > 999);

		int x = (quantity + (mapDVDquantity.get(title)));

		mapDVDquantity.put(title, x);

		try {

			log(04, title, x, "R", 000.00);

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in.nextLine();
	}

	public static void cancel() {
		// TODO Auto-generated method stub
		SystemFile.main(null);
	}

	public static void reTurn() {
		// TODO Auto-generated method stub
		/*
		 * return - return a previously rented copy of a DVD • should ask for
		 * the DVD title and number of copies • should add the number of copies
		 * to the number of available copies of the DVD • should save this
		 * information for the DVD transaction file • Constraints: o DVD title
		 * must be a current DVD title o at most 3 copies can be returned in one
		 * return transaction
		 */
		if (mapDVDstatus.isEmpty()) {
			System.out.println("No DVD requested for return");

			try {

				log(02, "-", 0, "-", 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			String title;
			do {
				System.out.println("Enter DVD title:");
				title = in.nextLine();
				title = title.toLowerCase();

				if (!mapDVDstatus.containsKey(title)) {
					System.out
							.println("Error: DVD with this name does not exist");
				}

			} while (!mapDVDstatus.containsKey(title));

			int quantity = 0;
			do {
				try {
					System.out.println("Enter number of copies:");
					quantity = in.nextInt();

				} // end try
				catch (InputMismatchException inputMismatchException) {
					System.err.printf("\nException: %s\n",
							inputMismatchException);
					in.nextLine(); // discard input so user can try again
					System.out
							.println("You must enter integers. Please try again.\n");
				}

				if (quantity <= 0 || quantity > 3) {
					System.out.println("Error: Invalid number of copies");
				}

			} while (quantity <= 0 || quantity > 3);

			mapDVDquantity.put(title, (mapDVDquantity.get(title) + quantity));
			System.out.println("transaction successful");

			try {

				log(02, title, quantity, mapDVDstatus.get(title), 000.00);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.nextLine();
		}

	}

	public static void log(int i, String title, int quantity, String string,
			double d) throws IOException {
		// TODO Auto-generated method stub

		String dvd_title = String.format("%-25s", title);
		String transaction_code = String.format("%02d", i);
		String number_of_copies = String.format("%03d", quantity);
		String status = String.format("%-1s", string).toUpperCase();
		DecimalFormat price = new DecimalFormat("000.00");
		String dvd_price = price.format(d);

		out.write('\n' + transaction_code + " " + dvd_title + " "
				+ number_of_copies + " " + status + " " + dvd_price);
		out.newLine();

	}

}
