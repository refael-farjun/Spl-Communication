package bgu.spl.net.api;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

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
	private HashMap<Short, ArrayList<String>> courses; // key is course number and the value is list of the rest details
	private ConcurrentHashMap<String, User> userConcurrentHashMap;
	private ConcurrentHashMap<Short, ArrayList<String>> studentInCourses;


	//to prevent user from creating new Database
	private Database() {
		// TODO: implement
		this.userConcurrentHashMap = new ConcurrentHashMap<>();
		this.studentInCourses = new ConcurrentHashMap<>();
		initialize("/home/spl211/Assignment3/Courses.txt");

	}

	public ConcurrentHashMap<Short, ArrayList<String>> getStudentInCourses() {
		return studentInCourses;
	}

	public HashMap<Short,  ArrayList<String>> getCourses(){
		return this.courses;
	}

	public void addUser(User user) {
		userConcurrentHashMap.put(user.getUserName(), user);
	}
	public ConcurrentHashMap<String, User> getUserConcurrentHashMap(){
		return this.userConcurrentHashMap;
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
		courses = new HashMap<>();
		try{
			File file = new File(coursesFilePath);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				String num = "";
				int i = 0;
				while (line.charAt(i) != '|'){ // find course number and put as a key in the map
					num += line.charAt(i);
					i++;
				}
				short number = Short.parseShort(num);
				courses.put(number, new ArrayList<>());
				String str = "";
				for (int j = i + 1; j < line.length(); j++){ // add values in the array list
					if (line.charAt(j) == '|'){
						courses.get(number).add(str);
						str = "";
						continue;
					}
					str += line.charAt(j);
				}
				courses.get(number).add(str);

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
