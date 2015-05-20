package distance;

import static org.junit.Assert.*;
import org.junit.Test;
import data.Bands;

public class MinkowskiDistanceTest {
	
	MinkowskiDistance distance = new MinkowskiDistance();
	
	@Test
	public void computerMinkowski() {
		Bands.initMap();
		double d = distance.computeMinkowski(1, Bands.hailey, Bands.veronica);
		assertEquals(2.0, d, 0.001);
		d = distance.computeMinkowski(1, Bands.hailey, Bands.jordyn);
		assertEquals(7.5, d, 0.001);
	}

	@Test
	public void findNearest() {
		Bands.initMap();
		Bands.initPeople();
		Bands.people.remove(Bands.haileyP);
		assertEquals("fail", Bands.veronicaP, distance.findNearest(1, Bands.haileyP, Bands.people));
		Bands.people.add(Bands.haileyP); Bands.people.remove(Bands.jordynP);
		assertEquals("fail", Bands.veronicaP, distance.findNearest(1, Bands.jordynP, Bands.people));
	}
}
