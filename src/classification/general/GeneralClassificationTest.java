package classification.general;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeneralClassificationTest {

	@Test
	public void correctnessTest() {
		GeneralClassification classification = new GeneralClassification();
		double correctMPG0 = classification.computeCorrectness(0, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(correctMPG0, 32, 0.1);
		double correctMPG1 = classification.computeCorrectness(1, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(correctMPG1, 56, 0.1);
		double correctMPG2 = classification.computeCorrectness(2, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(correctMPG2, 54, 0.1);
		double correctAthlete0 = classification.computeCorrectness(0, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(correctAthlete0, 80, 0.1);
		double correctAthlete1 = classification.computeCorrectness(1, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(correctAthlete1, 80, 0.1);
		double correctAthlete2 = classification.computeCorrectness(2, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(correctAthlete2, 80, 0.1);
		double correctIris0 = classification.computeCorrectness(0, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(correctIris0, 100, 0.1);
		double correctIris1 = classification.computeCorrectness(1, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(correctIris1, 93, 0.1);
		double correctIris2 = classification.computeCorrectness(2, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(correctIris2, 96, 0.1);
	}
	
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
