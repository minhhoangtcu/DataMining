package classification;

public class BasicClassificationTest {
	
	public static void main(String[] args) {
		BasicClassificationTest test = new BasicClassificationTest();
		test.testClassification(1);
		test.testClassification(2);
	}
	
	public void testClassification(int mode) {
		BasicClassification classification = new BasicClassification();
		AthletesLoad load = new AthletesLoad();
		Athlete[] allAthletes = load.loadFile(AthletesLoad.TEST);
		
		int corrects = 0;
		int numberOfTrials = allAthletes.length;
		for (Athlete athleteInArray: allAthletes) {
			String result = classification.classify(athleteInArray, mode);
			String expected = athleteInArray.getClassification();
			if (result.equals(expected)) corrects++;
		}
		
		double correctness = 100*corrects/numberOfTrials;
		if (mode == 1) {
			System.out.printf("Using no normalization, the classification is %.2f correct%n", correctness);
		}
		else if (mode == 2) {
			System.out.printf("Using normalization, the classification is %.2f correct%n", correctness);
		}
	}

}
