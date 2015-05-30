package book;

import org.junit.Test;

import distance.Person;

public class LoadTest {

	LoadFile book = new LoadFile("book");
	
	public void userLoadTest() {
		book.initUsers();
	}
	
	@Test
	public void ratingLoadTest() {
		book.initUsers();
		book.initRating();
		int counter = 0;
		for (Person person: book.people.values()) {
			if (counter >= 30) break;
			person.display();
			counter++;
		}
	}

	public void movieLoadTest() {
		LoadFile movie = new LoadFile("movie");
		movie.initRating();
	}
	
}
