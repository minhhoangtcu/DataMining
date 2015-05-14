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
	Map<Integer, Person> people = new HashMap<Integer, Person>();
	
	public void loadUsers() {
		BufferedReader reader = null;
		String line = null;
		int numberOfPeople = 0;
		
		try {
			reader = new BufferedReader(new FileReader(userFile));
			reader.readLine(); // skip first line
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(";");
				int id = Integer.parseInt(stripPunctionMark(fields[0])); // strip all " from the input and parse the int
				String location = stripPunctionMark(fields[1]);
				if (stripPunctionMark(fields[2]).equals("NULL")) {
					System.out.printf("Added %d at %s\n", id, location);
					people.put(id, new Person(id, location));
					numberOfPeople++;
				}
				else {
					int age = Integer.parseInt(stripPunctionMark(fields[2]));
					System.out.printf("Added %d at: %s age: %d \n", id, location, age);
					numberOfPeople++;
				}
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.err.println("Cannot find the provided user file");
		} catch (IOException e) {
			//e.printStackTrace();
			System.err.println("Cannot read from the file");
		}
		finally {
			try {
				reader.close();
				System.out.println("Total number of entries: " + numberOfPeople);
			} catch (IOException e) {
				//e.printStackTrace();
				System.err.println("Cannot close users reader");
				
			}
		}
	}

	private String stripPunctionMark(String text) {
		return text.replaceAll("\"", "");
	}

	
}
