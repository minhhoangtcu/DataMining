package book;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import distance.Person;

public class LoadFile {
	
	String userFile = "D:\\Computer Science\\BX-Users.csv";
	String ratingFile = "D:\\Computer Science\\BX-Book-Ratings.csv";
	Map<Integer, Person> people = new HashMap<Integer, Person>();
	
	public static void main(String[] args) {
		LoadFile load = new LoadFile();
		load.loadUsers();
		load.loadRating();
	}
	
	public void loadUsers() {
		BufferedReader reader = null;
		String line = null;
		long startTime = System.currentTimeMillis();
		
		try {
			reader = new BufferedReader(new FileReader(userFile));
			reader.readLine(); // skip first line
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\";\"");
				int id = Integer.parseInt(stripPunctionMark(fields[0]));
				String location = fields[1];
				if (location.contains("\";")) { // When there is no age. The data is represented as "location";NULL. We cannot split for ";" here. So check for "; and split.
					location = location.split("\";")[0];
					//System.out.printf("Added %d at %s\n", id, location);
					people.put(id, new Person(id, location));
				}
				else {
					int age = Integer.parseInt(stripPunctionMark(fields[2]));
					people.put(id, new Person(id, location, age));
					//System.out.printf("Added %d at: %s age: %d \n", id, location, age);
				}
			}
		} catch (NumberFormatException e) {
			System.err.println("Invalid input. Cannot convert to number");
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the provided user file");
		} catch (IOException e) {
			System.err.println("Cannot read from the file");
		}
		finally {
			try {
				reader.close();
				System.out.println("Execution time to add users (mili sec): " + (System.currentTimeMillis() - startTime));
			} catch (IOException e) {
				System.err.println("Cannot close users reader");
			}
		}
	}

	public void loadRating() {
		BufferedReader reader = null;
		String line = null;
		long startTime = System.currentTimeMillis();
		
		try {
			reader = new BufferedReader(new FileReader(ratingFile));
			reader.readLine(); // skip first line
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\";\"");
				int id = Integer.parseInt(stripPunctionMark(fields[0]));
				String isbn = stripPunctionMark(fields[1]);
				int score = Integer.parseInt(stripPunctionMark(fields[2]));
				people.get(id).putRating(isbn, (double) score);
				//System.out.printf("Added rating of id: %d for book isbn: %s at %d \n", id, isbn, score);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the provided rating file");
		} catch (IOException e) {
			System.err.println("Cannot read from the file");
		}
		finally {
			try {
				reader.close();
				System.out.println("Execution time to add rating (mili sec): " + (System.currentTimeMillis() - startTime));
			} catch (IOException e) {
				System.err.println("Cannot close rating reader");
				
			}
		}
	}

	private String stripPunctionMark(String text) {
		return text.replaceAll("\"", "");
	}
}
