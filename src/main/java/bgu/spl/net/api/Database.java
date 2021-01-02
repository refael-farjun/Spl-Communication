package bgu.spl.net.api;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
	private static Database database = new Database();


	//to prevent user from creating new Database
	private Database() {
		// TODO: implement



	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return database;
	}
	
	/**
	 * loades the courses from the file path specified 
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		// TODO: implement
		try{
			File file = new File(coursesFilePath);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred: File not found");
			e.printStackTrace();
		}
		return false;
	}


}
