package book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import distance.Person;

public class SortUtil {
	
	public static ArrayList<Pair<Double, Person>> getTopK(Map<Double, Person> source) {
		TreeMap<Double, Person> sortedMap = new TreeMap<Double, Person>(new Comparator<Double>() {
			public int compare(Double firstKey, Double secondKey) {
				if (firstKey > secondKey) return 1;
				else if (firstKey < secondKey) return -1;
				else return 0;
			}
			
		});
		
		
		
		
	}

}
