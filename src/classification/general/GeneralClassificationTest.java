package classification.general;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeneralClassificationTest {

	@Test
	public void predictionTest() {
		GeneralClassification classification = new GeneralClassification();
		String[] predictTest = classification.predict(0, 1, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(50, predictTest.length);
		for (String ele: predictTest) {
			System.out.print(ele + " ");
		}
	}
	
	@Test
	public void correctnessTest() {
		GeneralClassification classification = new GeneralClassification();
		double correctMPG0 = classification.computeCorrectness(0, 1, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(32, correctMPG0, 0.1);
		double correctMPG1 = classification.computeCorrectness(1, 1, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(56, correctMPG1, 0.1);
		double correctMPG2 = classification.computeCorrectness(2, 1, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(54, correctMPG2, 0.1);
		double correctAthlete0 = classification.computeCorrectness(0, 1, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(80, correctAthlete0, 0.1);
		double correctAthlete1 = classification.computeCorrectness(1, 1, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(80, correctAthlete1, 0.1);
		double correctAthlete2 = classification.computeCorrectness(2, 1, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(80, correctAthlete2, 0.1);
		double correctIris0 = classification.computeCorrectness(0, 1, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(100, correctIris0, 0.1);
		double correctIris1 = classification.computeCorrectness(1, 1, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(93, correctIris1, 0.1);
		double correctIris2 = classification.computeCorrectness(2, 1, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(96, correctIris2, 0.1);
	}
	
	@Test
	public void classifyNearestK() {
		GeneralClassification classification = new GeneralClassification();
		double correctMPG0 = classification.computeCorrectness(0, 2, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(46, correctMPG0, 0.1);
		double correctMPG1 = classification.computeCorrectness(1, 2, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(66, correctMPG1, 0.1);
		double correctMPG2 = classification.computeCorrectness(2, 2, GeneralLoad.MPGTRAINING, GeneralLoad.MPGTEST);
		assertEquals(62, correctMPG2, 0.1);
		double correctAthlete0 = classification.computeCorrectness(0, 2, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(80, correctAthlete0, 0.1);
		double correctAthlete1 = classification.computeCorrectness(1, 2, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(80, correctAthlete1, 0.1);
		double correctAthlete2 = classification.computeCorrectness(2, 2, GeneralLoad.ATHLETETRAINING, GeneralLoad.ATHLETETEST);
		assertEquals(80, correctAthlete2, 0.1);
		double correctIris0 = classification.computeCorrectness(0, 2, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(100, correctIris0, 0.1);
		double correctIris1 = classification.computeCorrectness(1, 2, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(100, correctIris1, 0.1);
		double correctIris2 = classification.computeCorrectness(2, 2, GeneralLoad.IRISTRAINING, GeneralLoad.IRISTEST);
		assertEquals(100, correctIris2, 0.1);
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
