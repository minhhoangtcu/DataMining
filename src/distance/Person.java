package distance;

import java.util.Map;

public class Person {
	private String name;
	private Map<String, Double> rating;
	
	public Person(String name, Map<String, Double> rating) {
		this.name = name;
		this.rating = rating;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String, Double> getRating() {
		return rating;
	}

	public String toString() {
		return name;
	}
	
}