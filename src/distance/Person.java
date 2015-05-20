package distance;
import java.util.HashMap;
import java.util.Map;

public class Person {
	private String name;
	private int id;
	private String location;
	private int age;
	private Map<String, Double> bookRating;
	private Map<Integer, Double> movieRating;
	
	public Person(String name, Map<String, Double> rating) {
		this.name = name;
		this.bookRating = rating;
		this.location = null;
		this.age = -1;
		this.id = -1;
	}
	
	public Person(int id, String location, int age) {
		this.id = id;
		this.name = null;
		this.location = location;
		this.age = age;
		bookRating = new HashMap<>();
	}
	
	public Person(int id, String location) {
		this.id = id;
		this.name = null;
		this.location = location;
		this.age = -1;
		bookRating = new HashMap<>();
	}
	
	public Person(int id) {
		this.id = id;
		this.name = null;
		this.location = null;
		this.age = -1;
		bookRating = new HashMap<>();
	}
	
	public void putRating(String productID, Double score) {
		bookRating.put(productID, score);
	}
	
	public void putRating(int productID, Double score) {
		movieRating.put(productID, score);
	}
	
	public int getID() {
		return id;
	}
	
	public String getLocation() {
		return location;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String, Double> getBookRating() {
		return bookRating;
	}
	
	public Map<Integer, Double> getMovieRating() {
		return movieRating;
	}

	public String toString() {
		return name;
	}
}