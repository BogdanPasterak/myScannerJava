package myScanner;

import java.util.Random;
import java.util.Scanner;
import java.lang.reflect.Field;


/**
 * Wrapper Class to Scanner. - Opens the channel once. - It displays information
 * about the data needed. - If an empty string has been entered and permission
 * is given returns a string empty or zero. - Supports String, int and double
 *
 * @author Bogdan Pasterak
 * @version 1.0
 * @since 21 Sep 2018
 */

public class MyScanner2 {
	private static Random rand;
	private static Scanner sc;

	private static String takenS;
	private static int takenI;
	private static double takenD;
	private static char takenC;

	// boolean
	public static final boolean NOT_EMPTY = true;
	public static final boolean CAN_BE_EMPTY = false;
	public static final boolean CAN_NOT_BE_ZERO = false;
	public static final boolean CAN_BE_ZERO = true;
	
	public static final class STRINGS {
		// the class draws (with empty string) a string from the selected category
		// to add categories, create a public string with the category name
		// and private string array with this name
		public static final String FIRST_NAME = "first_name";
	    @SuppressWarnings("unused")
		private static final String[] first_name = {"Bogdan", "Jon", "Lise", "Max", "Niels", "Patty", "Richard"};
		public static final String SURNAME = "surname";
		@SuppressWarnings("unused")
		private static final String[] surname = {"Pasterak", "Doe", "Curie", "Planck", "Bohr", "Wotson"};
		public static final String NAME = "name";
		@SuppressWarnings("unused")
		private static final String[] name = {"Bogdan Pasterak", "Jon Doe", "Jacqueline K. Barton", "Gertrude B. Elion", "Enrico Fermi", "Frieda Robscheit-Robbins"};
		public static final String DEPARTMENT = "department";
		@SuppressWarnings("unused")
		private static final String[] department = {"Architecture", "Economics", "Geosciences", "IT", "Transportation", "Music"};
		
	    private static String[] fieldNames;
		private static String[][] strings;
		
	    private static void init() {
	        // rewrites all arrays to 2dim array strings and names to fieldNames
	    	if (strings == null) {
				Field[] fields = STRINGS.class.getFields();
				Field[] allFields = STRINGS.class.getDeclaredFields();

				strings = new String[fields.length][];
				fieldNames = new String[fields.length];

				for (int i = 0; i < fields.length; i++) {
					String fieldName;
					if (fields[i].getType() == String.class) {
						try {
							boolean is = false;
							fieldName = (String) fields[i].get(String.class);
							fieldNames[i] = fieldName;

							for (Field field : allFields) {
								if (field.getName().equals(fieldName)) {
									strings[i] = (String[]) field.get(String[].class);
									is = true;
									break;
								}
							}
							if (!is) {
								strings[i] = new String[] { "no data" };
							}
						} catch (IllegalAccessException e) {
							strings[i] = new String[] { "no data" };
							fieldNames[i] = "no name";
						}
					} else {
						strings[i] = new String[] { "no data" };
						fieldNames[i] = "not String";
					}
				}
				// test
				/*
				for (int i = 0; i < strings.length; i++) {
					System.out.println("Name  " + fieldNames[i]);
					for (String s : strings[i]) {
						System.out.print(s + ", ");
					}
					System.out.println();
				}
				*/
	    	}
	    }



		protected static String getString(String category) {
			// Draws a String from the selected category
			init();
			//System.out.println(category);
			
			for (int i = 0; i < fieldNames.length; i++) {
				if (fieldNames[i].equals(category)) {
					return getStringCustom(strings[i]);
				}
			}
			
			return category;
		}

		protected static String getStringCustom(String... examples) {
			// Draws from custom examples
			return examples[rand.nextInt(examples.length)];
		}
		
	}

	// char restricts
	public static final class CHARS {
		public static final String ALL_CHAR = "all_char";
		public static final String FROM_A_TO_Z = "A-Z";
		public static final String FROM_a_TO_z = "a-z";
		public static final String ALL_LETTERS = "a-zA-Z";
		public static final String ALL_DIGITS = "0-9";
		public static final String ALL_LETTERS_DIGITS = "a-zA-Z0-9";
		public static final String WITH_SPACE = "space";
		public static final String WITH_UNDERSCORE = "underscore";
		public static final String WITH_WHITE_SIGNS = "white-signs";
		public static final String ALL_LETTERS_DIGITS_WHITE_SIGNS = "a-zA-Z0-9 _";
		private static final String[] restrictes = { "all_char", "A-Z", "a-z", "a-zA-Z", "0-9", "a-zA-Z0-9", "space",
				"underscore", "white-signs", "a-zA-Z0-9 _" };

		protected static boolean isRestrict(String restrict) {
			for (int i = 0; i < restrictes.length; i++) {
				if (restrictes[i].equals(restrict))
					return true;
			}
			return false;
		}

		protected static String addAllowedRestrict(String allowed, String restrict) {
			String addAllowed;

			switch (restrict) {
			case "A-Z":
				addAllowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				break;
			case "a-z":
				addAllowed = "abcdefghijklmnopqrstuvwxyz";
				break;
			case "a-zA-Z":
				addAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
				break;
			case "0-9":
				addAllowed = "0123456789";
				break;
			case "a-zA-Z0-9":
				addAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
				break;
			case "a-zA-Z0-9 _":
				addAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 _";
				break;
			case "space":
				addAllowed = " ";
				break;
			case "underscore":
				addAllowed = "_";
				break;
			case "white-signs":
				addAllowed = " _";
				break;

			default: // all_char
				addAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 _\\/|?<>,.~#@':;}] {[+=-)(*&^%$\"!€£";
				break;
			}
			
			return addAllowed(allowed, addAllowed);
		}

		protected static String addAllowed(String allowed, String addAllowed) {
			// adding
			for (int i = 0; i < addAllowed.length(); i++) {
				if (!allowed.contains(addAllowed.substring(i, i + 1))) {
					allowed += addAllowed.substring(i, i + 1);
				}
			}

			return allowed;
		}
	}

	// if scanner object does not exist yet
	private static void isScanner() {
		if (sc == null) {
			sc = new Scanner(System.in);
			rand = new Random();
		}
	}

	//--------------------------------------------------------------
	// input line
	public static String getString() {

		return getString("", CAN_BE_EMPTY);
	}

	public static String getString(boolean notEmpty) {

		return getString("", notEmpty);
	}

	public static String getString(boolean notEmpty, String... example) {
		// ignore notEmpty, have to be able empty
		
		getString("");
		
		if (takenS.length() == 0) {
			takenS = STRINGS.getStringCustom(example);
		}

		return takenS;
	}

	// input with info
	public static String getString(String info) {

		isScanner();

		if (info.length() == 0)
			info = "Type line : ";
		else if (info.endsWith(":"))
			info += " ";
		else if (!info.endsWith(": "))
			info += " : ";
		
		System.out.print(info);
		takenS = sc.nextLine();

		return takenS;
	}

	public static String getString(String info, boolean notEmpty) {

		getString(info);

		while (notEmpty && takenS.length() == 0) {
			System.out.println("Try again, String can't be empty");
			getString(info);
		}

		return takenS;
	}

	public static String getString(String info, String category ) {

		getString(info);

		if (takenS.length() == 0) {
			//System.out.println("drow");
			takenS = STRINGS.getString( category );
		}

		return takenS;
	}

	public static String getString(String... infoAndExample ) {

		getString(infoAndExample[0]);

		if (takenS.length() == 0) {
			String[] example = new String[infoAndExample.length - 1];
			for (int i = 0; i < example.length; i++) {
				example[i] = infoAndExample[i + 1];
			}
			takenS = STRINGS.getStringCustom(example);
		}

		return takenS;
	}

	//--------------------------------------------------------------
	// input int (if enter set 0)
	public static int getInt() {

		return getInt("", CAN_BE_ZERO);
	}

	public static int getInt(boolean canBeZero) {

		return getInt("", canBeZero);
	}

	public static int getInt(String info) {

		return getInt(info, CAN_BE_ZERO);
	}

	public static int getInt(String info, boolean canBeZero) {
		boolean ok = true;

		isScanner();

		if (info.length() == 0)
			info = "Type \"int\" : ";
		else if (info.endsWith(":"))
			info += " ";
		else if (!info.endsWith(": "))
			info += " : ";

		// until the format is OK
		do {
			// do not write the first time
			if (!ok)
				System.out.println("Try again, it was not \"int\" type");

			System.out.print(info);
			takenS = sc.nextLine();
			try {
				takenI = Integer.parseInt(takenS);
				ok = true;
			} catch (Exception e) {
				if (canBeZero && takenS.equals("")) {
					takenI = rand.nextInt(100);
					System.out.println(takenI);
					ok = true;
				} else
					ok = false;
			}
		} while (!ok);

		return takenI;
	}

	// range to int
	public static int getInt(int from, int to) {

		return getInt(from, to, CAN_BE_ZERO);
	}

	public static int getInt(int from, int to, boolean canBeZero) {
		String info = "Type \"int\" from " + from + " to " + to + " : ";

		return getInt(info, from, to, canBeZero);
	}

	public static int getInt(String info, int from, int to, boolean canBeZero) {
		boolean repeat = false;
		
		if (from > to)
			throw new IllegalArgumentException("FROM it is bigger than TO");

		do {
			if (repeat)
				System.out.println("Out of range, try again");
			else
				repeat = true;
			getInt(info, canBeZero);
			// if tekenS is empty random number from range!
			if (takenS.equals("")) {
				takenI = rand.nextInt(to - from + 1) + from;
				System.out.println(takenI);
			}
		} while (takenI < from || takenI > to);

		return takenI;
	}

	public static int getInt(int to) {

		return getInt(0, to, CAN_BE_ZERO);
	}

	public static int getInt(int to, boolean canBeZer) {

		return getInt(0, to, canBeZer);
	}

	public static int getInt(String info, int to) {

		return getInt(info, 0, to, CAN_BE_ZERO);
	}

	public static int getInt(String info, int to, boolean canBeZero) {

		return getInt(info, 0, to, canBeZero);
	}

	public static int getInt(String info, int from, int to) {

		return getInt(info, from, to, CAN_BE_ZERO);
	}

	//--------------------------------------------------------------
	// input double
	public static double getDouble() {

		return getDouble("Type \"double\" : ", CAN_BE_ZERO);
	}

	public static double getDouble(boolean canBeZero) {

		return getDouble("Type \"double\" : ", canBeZero);
	}

	public static double getDouble(String info) {

		return getDouble(info, CAN_BE_ZERO);
	}

	public static double getDouble(String info, boolean canBeZero) {
		boolean ok = true;

		isScanner();

		if (info.length() == 0)
			info = "Type \"double\" : ";
		else if (info.endsWith(":"))
			info += " ";
		else if (!info.endsWith(": "))
			info += " : ";

		// until the format is OK
		do {
			// do not write the first time
			if (!ok)
				System.out.println("Try again, it was not \"double\" type");

			System.out.print(info);
			takenS = sc.nextLine();
			try {
				takenD = Double.parseDouble(takenS);
				ok = true;
			} catch (Exception e) {
				if (canBeZero && takenS.equals("")) {
					takenD = rand.nextDouble() * 100;
					System.out.println(takenD);
					ok = true;
				} else
					ok = false;
			}
		} while (!ok);

		return takenD;
	}

	public static double getDouble(double to) {

		return getDouble( 0.0, to, CAN_BE_ZERO);
	}

	public static double getDouble(double to, boolean canBeZero) {

		return getDouble( 0.0, to, canBeZero);
	}

	public static double getDouble(String info, double to) {

		return getDouble(info, 0.0, to, CAN_BE_ZERO);
	}

	public static double getDouble(String info, double to, boolean canBeZero) {

		return getDouble(info, 0.0, to, canBeZero);
	}

	public static double getDouble(double from, double to) {

		return getDouble( from, to, CAN_BE_ZERO);
	}

	public static double getDouble(String info, double from, double to) {

		return getDouble(info, from, to, CAN_BE_ZERO);
	}

	public static double getDouble(double from, double to, boolean canBeZero) {
		String info = "Type \"double\" from " + from + " to " + to + " : ";

		return getDouble(info, from, to, canBeZero);
	}

	public static double getDouble(String info, double from, double to, boolean canBeZero) {
		boolean repeat = false;

		do {
			if (repeat)
				System.out.println("Out of range, try again");
			else
				repeat = true;
			getDouble(info, canBeZero);
			// random number
			if (takenS.equals("")) {
				takenD = rand.nextDouble() * (to - from) + from;
				System.out.println(takenD);
			}
		} while (takenD < from || takenD > to);

		return takenD;
	}

	//--------------------------------------------------------------
	// input character
	public static char getChar() {

		return getChar("", CAN_BE_EMPTY);
	}

	public static char getChar(String info) {

		return getChar(info, CAN_BE_EMPTY);
	}

	public static char getChar(boolean notEmpty) {

		return getChar("", notEmpty);
	}

	public static char getChar(boolean notEmpty, String...restricts ) {

		return getChar("", notEmpty, restricts);
	}

	public static char getChar(String info, boolean notEmpty) {

		return getChar("", notEmpty, CHARS.ALL_CHAR);
	}

	public static char getChar(String... infoAndRestricts) {

		String[] restricts = new String[infoAndRestricts.length - 1];

		for (int i = 0; i < restricts.length; i++) {
			restricts[i] = infoAndRestricts[i + 1];
		}

		return getChar(infoAndRestricts[0], CAN_BE_EMPTY, restricts);
	}

	public static char getChar(String info, boolean notEmpty, String... restricts) {
		boolean ok = false;
		String allowed = "";

		isScanner();

		if (info.length() == 0)
			info = "Type \"char\" : ";
		else if (info.endsWith(":"))
			info += " ";
		else if (!info.endsWith(": "))
			info += " : ";

		// check whether it is allowed
		for (int i = 0; i < restricts.length; i++) {
			if (CHARS.isRestrict(restricts[i])) {
				// System.out.println("RESTRICT ! " + restricts[i]);
				allowed = CHARS.addAllowedRestrict(allowed, restricts[i]);
			} else {
				// System.out.println("CUSTOM !! " + restricts[i]);
				allowed = CHARS.addAllowed(allowed, restricts[i]);
			}
			//System.out.println(allowed);
		}

		do {
			System.out.print(info);
			takenS = sc.nextLine();

			if (takenS.length() == 0) {
				// if ENTER
				if (notEmpty) {
					System.out.println("Try again, type some character");
				} else {
					// random of allowed
					takenC = allowed.charAt(rand.nextInt(allowed.length()));
					ok = true;
				}
			} else if ( allowed.contains(takenS.substring(0, 1)) ) {
				// matched
				takenC = takenS.charAt(0);
				ok = true;
			} else {
				// not matched
					System.out.println("Out of range, acceptable characters :");
					// writing out permissible characters in rows after 16
					for (int i = 0; i < allowed.length(); i++) {
						System.out.print(allowed.substring(i, i+1) + 
								((i == allowed.length() - 1) ? ".\n" : 
									((i % 16 == 15) ? "  and\n" : ", ")));
					}
			} 

		} while (!ok);

		return takenC;
	}

}
