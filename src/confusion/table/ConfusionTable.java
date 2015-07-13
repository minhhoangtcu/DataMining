package confusion.table;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

public class ConfusionTable {
	
	public static void main(String[] args) {
		String[] actual = {"England", "England", "US"};
		String[] predict = {"England", "US", "US"};
		printTable(actual, predict);
	}
	
	public static void printTable(String[] actual, String[] predict) {
		
		String[] uniqueActual = new HashSet<String>(Arrays.asList(actual)).toArray(new String[0]); // We will use this as a general list of unique classifications
		String[] uniquePredict = new HashSet<String>(Arrays.asList(predict)).toArray(new String[0]);
		String[] combinedClassifications = Stream.concat(Arrays.stream(uniqueActual), Arrays.stream(uniquePredict)).toArray(String[]::new);
		String[] unique = new HashSet<String>(Arrays.asList(combinedClassifications)).toArray(new String[0]);
		List<String> sortedClassNames = new ArrayList<String>();
		for (String classification: unique) {
			sortedClassNames.add(classification);
		}		
		Collections.sort(sortedClassNames);
		unique = sortedClassNames.toArray(new String[0]);
		
		if ((actual.length != predict.length)) {
			System.err.println("Actual and predict array must have same length or classifications.");
			throw new IllegalArgumentException("Actual and predict array must have same length or classifications.");
		}
		else {
			// Initialize confusion matrix with value of 0
			HashMap<String,Integer> cmatrix = new HashMap<String,Integer>();
			for (String actualClassName : unique) {
				for (String predictedClassName : unique) {
					String key = actualClassName + "|" + predictedClassName; // We use only 1 list of unique
					cmatrix.put(key, 0);
				}
			}
			
			// Put value into confusion matrix
			int numberOfObservations = actual.length;
			for (int i = 0; i < numberOfObservations; i++) {
				String key = actual[i] + "|" + predict[i];		
				int value = cmatrix.get(key);
				value++;
				cmatrix.put(key, value);
			}			
			
			// Print confusion matrix
			System.out.print("p/a");
			for(String predictedClassName : unique) {
			    System.out.print("\t" + predictedClassName);
			}
			System.out.println();
			
			for(String actualClassName : unique) {
			    System.out.print(actualClassName);
			    for(String predictedClassName : unique) {
			        Integer value = cmatrix.get(actualClassName + "|" + predictedClassName);
			        System.out.print("\t");
			        if(value != null) {
			            System.out.print(value);
			        }
			    }
			    System.out.println();
			}
		}
	}
}		
	