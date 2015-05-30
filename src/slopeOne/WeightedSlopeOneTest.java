package slopeOne;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import distance.Person;

public class WeightedSlopeOneTest {

	public void initData() {
		Map<String, Double> amyRating = new HashMap<String, Double>();
		amyRating.put("Taylor Swift", 4.0);
		amyRating.put("PSY", 3.0);
		amyRating.put("Whitney Houston", 4.0);
		Person amy = new Person("Amy", amyRating);
		
		Map<String, Double> benRating = new HashMap<String, Double>();
		benRating.put("Taylor Swift", 5.0);
		benRating.put("PSY", 2.0);
		Person ben = new Person("Ben", benRating);
		
		Map<String, Double> claraRating = new HashMap<String, Double>();
		claraRating.put("PSY", 3.5);
		claraRating.put("Whitney Houston", 4.0);
		Person clara = new Person("Clara", claraRating);
		
		Map<String, Double> daisyRating = new HashMap<String, Double>();
		daisyRating.put("Taylor Swift", 5.0);
		daisyRating.put("Whitney Houston", 3.0);
		Person daisy = new Person("Daisy", daisyRating);
		
		Person[] people = {amy, ben, clara, daisy};
		
		Map<String, Double> minhRating = new HashMap<String, Double>();
		minhRating.put("Taylor Swift", 5.0);
		minhRating.put("PSY", 2.0);
		Person minh = new Person("Minh", minhRating);
		
		WeightedSlopeOne slopeOne = new WeightedSlopeOne();
		
		slopeOne.computeDeviation(people);
		//slopeOne.printDeviationsTable();
		System.out.println(slopeOne.predict(minh, "Whitney Houston"));
		//slopeOne.recommend(minh);
	}
	
	
	@Test
	public void test() {
		initData();
	}

}
