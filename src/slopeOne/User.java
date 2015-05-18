package slopeOne;

import java.util.Map;

public class User<T> {
	private String name;
	private Map<String, T> rating;
	
	public User(String name, Map<String, T> rating) {
		this.name = name;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}
	
	public Map<String, T> getRating() {
		return rating;
	}
	
}
