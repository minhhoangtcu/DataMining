package classification;

import static org.junit.Assert.*;
import classification.ModifiedNormalization;

import org.apache.commons.math3.ml.distance.ManhattanDistance;
import org.junit.Test;

public class ModifiedNormalizationTest {

	@Test
	public void testMedian() {
		ModifiedNormalization normal = new ModifiedNormalization();
		int[] test1 = {1, 2, 3, 4, 5};
		assertEquals(normal.getMedian(test1), 3.0, 0.001);
		int[] test2 = {0, 7, 2, 1234, 3};
		assertEquals(normal.getMedian(test2), 3.0, 0.001);
		int[] test3 = {-100, -23, 0, 0, 23};
		assertEquals(normal.getMedian(test3), 0.0, 0.001);
		int[] test4 = {0, 0, 1, 2, 3, 6};
		assertEquals(normal.getMedian(test4), 1.5, 0.001);
	}
	
	@Test
	public void testAbsoluteSD() {
		ModifiedNormalization normal = new ModifiedNormalization();
		int[] test1 = {43000, 45000, 55000, 69000, 70000, 75000, 105000, 115000};
		assertEquals(normal.getAbsoluteSD(test1), 19125.0, 0.001);
	}

	@Test
	public void testModifiedNormalization() {
		ModifiedNormalization normal = new ModifiedNormalization();
		int[] test1 = {43000, 45000, 55000, 69000, 70000, 75000, 105000, 115000};
		double[] normalized = normal.getModifiedNormalization(test1);
		assertEquals(normalized[5], 0.2876, 0.0001);
		//for (double value: normalized) {
		//	System.out.println(value);
		//}
	}
	
	@Test
	public void testDistance() {
		ManhattanDistance distance = new ManhattanDistance();
		double[] f1 = {-1.933, -1.218};
		double[] f2 = {-2.773, -0.505};
		System.out.println(distance.compute(f1, f2));
	}
}
