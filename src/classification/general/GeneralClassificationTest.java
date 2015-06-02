package classification.general;

import static org.junit.Assert.*;
import org.junit.Test;

public class GeneralClassificationTest {

	@Test
	public void loadMPGTest() {
		GeneralLoad load = new GeneralLoad(GeneralLoad.MPGTRAINING);
		load.printValues();
	}
	
	@Test
	public void itemEqualTest() {
		String classificationName = "20";
		double[] attributes = {8, 307.0, 130.0, 3504, 12.0};
		String[] comments = {"chevrolet chevelle malibu"};
		Item testItem = new Item(classificationName, attributes, comments);
		
		String classificationName2 = "20";
		double[] attributes2 = {8, 307.0, 130.0, 3504, 12.0};
		String[] comments2 = {"chevrolet chevelle malibu"};
		Item testItem2 = new Item(classificationName2, attributes2, comments2);
		
		String classificationName3 = "30";
		double[] attributes3 = {8, 307.0, 130.0, 3504, 12.0};
		String[] comments3 = {"chevrolet chevelle malibu"};
		Item testItem3 = new Item(classificationName3, attributes3, comments3);
		
		assertTrue(testItem.equals(testItem2));
		assertFalse(testItem.equals(testItem3));
		assertFalse(testItem3.equals(testItem2));
	}

}
