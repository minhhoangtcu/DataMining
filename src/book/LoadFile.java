package book;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import distance.Person;

public class LoadFile {
	
	private int mode = 1; // Default to read book
	private static final String USERFILE = "data\\BX-Users.csv";
	private static final String BOOKRATINGFILE = "data\\BX-Book-Ratings.csv";
	//private static final String MOVIERATINGFILE = "src\\MoviesData\\smallest ratings.csv"; // 10 lines of data.
	private static final String MOVIERATINGFILE = "src\\MoviesData\\smaller rating2.csv"; // 10000 lines of data.
	//private static final String MOVIERATINGFILE = "src\\MoviesData\\smaller ratings.csv"; // 50000 lines of data.
	private String userFileDir;
	private String ratingFileDir;
	Map<Integer, Person> people = new HashMap<Integer, Person>();
	
	public static void main(String[] args) {
		LoadFile movie = new LoadFile("movie");
		movie.initRating();
	}
	
	public LoadFile(String mode) {
		if (mode.equals("movie")) {
			this.mode = 2;
			ratingFileDir = MOVIERATINGFILE;
		}
		else {
			userFileDir = USERFILE;
			ratingFileDir = BOOKRATINGFILE;
			this.mode = 1;
		}
	}
	
	public void initUsers() {
		BufferedReader reader = null;
		String line = null;
		long startTime = System.currentTimeMillis();
		
		try {
			reader = new BufferedReader(new FileReader(userFileDir));
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

	public void initRating() {
		BufferedReader reader = null;
		String line = null;
		long startTime = System.currentTimeMillis();
		
		try {
			reader = new BufferedReader(new FileReader(ratingFileDir));
			reader.readLine(); // skip first line
			while ((line = reader.readLine()) != null) {
				if (mode == 1) {
					String[] fields = line.split("\";\"");
					int id = Integer.parseInt(stripPunctionMark(fields[0]));
					String isbn = stripPunctionMark(fields[1]);
					int score = Integer.parseInt(stripPunctionMark(fields[2]));
					people.get(id).putRating(isbn, (double) score);
					//System.out.printf("Added rating of id: %d for book isbn: %s at %d \n", id, isbn, score);
				}
				else if (mode == 2) {
					String[] fields = line.split(",");
					int userID = Integer.parseInt(fields[0]);
					int movieID = Integer.parseInt(fields[1]);
					double score = Double.parseDouble(fields[2]);
					if (!people.containsKey(userID))
						people.put(userID, new Person(userID));
					people.get(userID).putRating(movieID, score);
					//System.out.printf("Added rating of id: %d for movieID: %d at %f \n", userID, movieID, score);
				}
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
	
	public Map<Integer, Person> getPeople() {
		return people;
	}
}
