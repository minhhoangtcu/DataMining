package book;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoadTest {

	LoadFile load = new LoadFile();
	
	@Test
	public void userLoadTest() {
		load.loadUsers();
	}
	
	@Test
	public void ratingLoadTest() {
		load.loadUsers();
		load.loadRating();
	}

}
