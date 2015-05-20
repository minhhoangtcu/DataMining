package book;

import org.junit.Test;

public class LoadTest {

	LoadFile book = new LoadFile("book");
	
	public void userLoadTest() {
		book.initUsers();
	}
	
	public void ratingLoadTest() {
		book.initUsers();
		book.initRating();
	}

	@Test
	public void movieLoadTest() {
		LoadFile movie = new LoadFile("movie");
		movie.initRating();
	}
	
}
