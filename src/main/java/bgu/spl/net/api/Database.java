package bgu.spl.net.api;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
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
	private ArrayList<String> courses;


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
		courses = new ArrayList<>();
		try{
			File file = new File(coursesFilePath);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				courses.add(line);
			}
			scanner.close();
			return true;
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred: File not found");
			e.printStackTrace();
		}
		return false;
	}


}
