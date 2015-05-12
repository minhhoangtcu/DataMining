package distance;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class MinkowskiDistanceTest {
	
	MinkowskiDistance distance = new MinkowskiDistance();
	Map<String, Double> angelica = new HashMap<String, Double>();
	
	
	public void initMap() {
		angelica.put("Blues Traveler", 3.5);
		angelica.put("Broken Bells", 2.0);
		angelica.put("Noral Jones", 4.5);
		angelica.put("Phoenix", 5.0);
		angelica.put("Slightly Stoopid", 1.5);
		angelica.put("The Strokes", 2.5);
		angelica.put("Campire Weekend", 2.0);
	}

	@Test
	public void computerMinkowski() {
	}

}
