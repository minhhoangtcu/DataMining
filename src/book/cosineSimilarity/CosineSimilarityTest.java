package book.cosineSimilarity;

import static org.junit.Assert.*;

import org.junit.Test;

import distance.Person;

public class CosineSimilarityTest {

	@Test
	public void test() {
		CosineSimilarity sim = new CosineSimilarity();
		
		Person clara = new Person("Clara");
		clara.putRating("Blues Traveler", 4.75);
		clara.putRating("Norah Jones", 4.5);
		clara.putRating("Phoenix", 5.0);
		clara.putRating("The Strokes", 4.25);
		clara.putRating("Weird AI", 4.0);
		Person robert = new Person("Robert");
		robert.putRating("Blues Traveler", 4.0);
		robert.putRating("Norah Jones", 3.0);
		robert.putRating("Phoenix", 5.0);
		robert.putRating("The Strokes", 2.0);
		robert.putRating("Weird AI", 1.0);
		
		assertEquals(0.935, sim.computeSimilarity(clara, robert), 0.001);
	}

}
