package distance;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Person {
	private String name;
	private int id;
	private String location;
	private int age;
	private Map<String, Double> rating;
	
	public Person(String name, Map<String, Double> rating) {
		this.name = name;
		this.rating = rating;
		this.location = null;
		this.age = -1;
		this.id = -1;
	}
	
	public Person(int id, String location, int age) {
		this.id = id;
		this.name = null;
		this.location = location;
		this.age = age;
		rating = new HashMap<>();
	}
	
	public Person(int id, String location) {
		this.id = id;
		this.name = null;
		this.location = location;
		this.age = -1;
		rating = new HashMap<>();
	}
	
	public Person(int id) {
		this.id = id;
		this.name = null;
		this.location = null;
		this.age = -1;
		rating = new HashMap<>();
	}
	
	public void display() {
		System.out.println("***************************************************");
		System.out.printf("ID: %d | Location: %s | Age: %d%n", id, location, age);
		if (rating.isEmpty()) {
			System.out.println("No Rating");
		}
		else {
			for (Entry<String, Double> entry: rating.entrySet()) {
				System.out.printf("Rated isbn %s: %.0f%n", entry.getKey(), entry.getValue());
			}
		}
	}
	
	public void putRating(String productID, Double score) {
		rating.put(productID, score);
	}
	
	public void putRating(int productID, Double score) {
		rating.put(productID + "", score);
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
	
	public Map<String, Double> getRating() {
		return rating;
	}
	
	public String toString() {
		return "Name: " + name + " with ID: "+ id;
	}
}
