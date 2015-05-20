package book;

import org.junit.Test;

public class LoadTest {

	LoadFile load = new LoadFile("book");
	
	@Test
	public void userLoadTest() {
		load.initUsers();
	}
	
	@Test
	public void ratingLoadTest() {
		load.initUsers();
		load.initRating();
	}

}
