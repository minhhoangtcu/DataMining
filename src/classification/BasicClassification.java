package classification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.apache.commons.math3.ml.distance.ManhattanDistance;

public class BasicClassification {
	
	public String classify(Athlete athlete, int mode) {
		Athlete closest = null;
		if (mode == 1)
			closest = findNearestMatch(athlete);
		else if (mode == 2)
			closest = findNearestMatchNormalized(athlete);
		
		
		if (closest != null) return closest.getClassification();
		else return null;
	}
	
	public Athlete findNearestMatchNormalized(Athlete athlete) {
		AthletesLoad load = new AthletesLoad();
		Athlete[] allAthletes = load.loadFile(AthletesLoad.TRAINING);
		allAthletes = addElementToArray(allAthletes, athlete);
		allAthletes = setNormalizedValues(allAthletes);
		TreeMap<Double, Athlete> distanceMap = new TreeMap<>();
		
		for (Athlete ethleteInArray: allAthletes) {
			if (!ethleteInArray.getName().equals(athlete.getName())) { //Ignore comparing with itself. ASSUME THAT NAMES ARE UNIQUE!!
				double distance = getNormalizedDistance(ethleteInArray, athlete);
				//System.out.printf("Distance between \t%s \t%s is \t%.2f%n", ethleteInArray.getName(), athlete.getName(), distance);
				distanceMap.put(distance, ethleteInArray);
			}
		}
		return distanceMap.firstEntry().getValue();
	}
	
	public Athlete[] addElementToArray(Athlete[] input, Athlete element) {
		ArrayList<Athlete> list = new ArrayList<Athlete>(Arrays.asList(input));
		list.add(element);
		return list.toArray(new Athlete[list.size()]);
	}
	
	public Athlete[] setNormalizedValues(Athlete[] allAthletes) {
		Normalization normal = new Normalization();
		int[] allWeight = new int[allAthletes.length];
		int[] allHeight = new int[allAthletes.length];
		for (int i = 0; i < allAthletes.length; i++) {
			allWeight[i] = allAthletes[i].getWeight();
			allHeight[i] = allAthletes[i].getHeight();
		}
		double[] normalizedWeight = normal.getModifiedNormalization(allWeight);
		double[] normalizedHeight = normal.getModifiedNormalization(allHeight);
		
		for (int i = 0; i < allAthletes.length; i++) {
			allAthletes[i].setNormalizedWeight(normalizedWeight[i]);
			allAthletes[i].setNormalizedHeight(normalizedHeight[i]);
			//System.out.println(normalizedHeight[i] + "\t" + normalizedWeight[i]); //DEBUGING PURPOSE
		}
		return allAthletes;
	}
	
	public Athlete findNearestMatch(Athlete athlete) {
		AthletesLoad load = new AthletesLoad();
		Athlete[] allAthletes = load.loadFile(AthletesLoad.TRAINING);
		TreeMap<Double, Athlete> distanceMap = new TreeMap<>();
		
		for (Athlete ethleteInArray: allAthletes) {
			if (!ethleteInArray.getName().equals(athlete.getName())) { //Ignore comparing with itself.
				double distance = getDistance(ethleteInArray, athlete);
				//System.out.printf("Distance between \t%s \t%s is \t%.2f%n", ethleteInArray.getName(), athlete.getName(), distance);
				distanceMap.put(distance, ethleteInArray);
			}
		}
		return distanceMap.firstEntry().getValue(); //First entry should be the closest distance
	}
	
	public double getDistance(Athlete firstPerson, Athlete secondPerson) {
		ManhattanDistance distance = new ManhattanDistance();
		return distance.compute(firstPerson.toVector(), secondPerson.toVector());
	}
	
	public double getNormalizedDistance(Athlete firstPerson, Athlete secondPerson) {
		ManhattanDistance distance = new ManhattanDistance();
		return distance.compute(firstPerson.toNormalizedVector(), secondPerson.toNormalizedVector());
	}
}
