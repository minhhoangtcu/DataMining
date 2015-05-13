package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import distance.Person;

public class Bands {
	
	public static Map<String, Double> angelica = new HashMap<String, Double>();
	public static Map<String, Double> bill = new HashMap<String, Double>();
	public static Map<String, Double> chan = new HashMap<String, Double>();
	public static Map<String, Double> dan = new HashMap<String, Double>();
	public static Map<String, Double> hailey = new HashMap<String, Double>();
	public static Map<String, Double> jordyn = new HashMap<String, Double>();
	public static Map<String, Double> sam = new HashMap<String, Double>();
	public static Map<String, Double> veronica = new HashMap<String, Double>();
	public static List<Person> people = new ArrayList<Person>();
	public static Person angelicaP, billP, chanP, danP, haileyP, jordynP, samP, veronicaP;
	
	public static void initMap() {
		angelica.put("Blues Traveler", 3.5);
		angelica.put("Broken Bells", 2.0);
		angelica.put("Noral Jones", 4.5);
		angelica.put("Phoenix", 5.0);
		angelica.put("Slightly Stoopid", 1.5);
		angelica.put("The Strokes", 2.5);
		angelica.put("Campire Weekend", 2.0);
		
		bill.put("Blues Traveler", 2.0);
		bill.put("Broken Bells", 3.5);
		bill.put("Deadmau5", 4.0);
		bill.put("Phoenix", 2.0);
		bill.put("Slightly Stoopid", 3.5);
		bill.put("Campire Weekend", 3.0);
		
		chan.put("Blues Traveler", 5.0);
		chan.put("Broken Bells", 1.0);
		chan.put("Deadmau5", 1.0);
		chan.put("Noral Jones", 3.0);
		chan.put("Phoenix", 5.0);
		chan.put("Slightly Stoopid", 1.0);
		
		dan.put("Blues Traveler", 3.0);
		dan.put("Broken Bells", 4.0);
		dan.put("Deadmau5", 4.5);
		dan.put("Phoenix", 3.0);
		dan.put("Slightly Stoopid", 4.5);
		dan.put("The Strokes", 4.0);
		dan.put("Campire Weekend", 2.0);
		
		hailey.put("Broken Bells", 4.0);
		hailey.put("Deadmau5", 1.0);
		hailey.put("Noral Jones", 4.0);
		hailey.put("The Strokes", 4.0);
		hailey.put("Campire Weekend", 1.0);
		
		jordyn.put("Broken Bells", 4.5);
		jordyn.put("Deadmau5", 4.0);
		jordyn.put("Noral Jones", 5.0);
		jordyn.put("Phoenix", 5.0);
		jordyn.put("Slightly Stoopid", 4.5);
		jordyn.put("The Strokes", 4.0);
		jordyn.put("Campire Weekend", 4.0);
		
		sam.put("Blues Traveler", 5.0);
		sam.put("Broken Bells", 2.0);
		sam.put("Noral Jones", 3.0);
		sam.put("Phoenix", 5.0);
		sam.put("Slightly Stoopid", 4.0);
		sam.put("The Strokes", 5.0);
		
		veronica.put("Blues Traveler", 3.0);
		veronica.put("Noral Jones", 5.0);
		veronica.put("Phoenix", 4.0);
		veronica.put("Slightly Stoopid", 2.5);
		veronica.put("The Strokes", 3.0);
	}

	public static void initPeople() {
		angelicaP = new Person("Angelica", angelica); people.add(angelicaP);
		billP = new Person("Bill", bill); people.add(billP);
		chanP = new Person("Chan", chan); people.add(chanP);
		danP = new Person("Dan", dan); people.add(danP);
		haileyP = new Person("Hailey", hailey); people.add(haileyP);
		jordynP = new Person("Jordyn", jordyn); people.add(jordynP);
		samP = new Person("Sam", sam); people.add(samP);
		veronicaP = new Person("Veronica", veronica); people.add(veronicaP);
	}
}
