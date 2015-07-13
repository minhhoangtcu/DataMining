package confusion.table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ConfusionTable {
	
	public static void main(String[] args) {
		String[] actual = {"England", "England", "US"};
		String[] predict = {"England", "US", "US"};
		printTable(actual, predict);
	}
	
	public static void printTable(String[] actual, String[] predict) {
		String[] uniqueActual = new HashSet<String>(Arrays.asList(actual)).toArray(new String[0]); // We will use this as a general list of unique classifications
		String[] uniquePredict = new HashSet<String>(Arrays.asList(predict)).toArray(new String[0]);
		if ((!Arrays.equals(uniqueActual, uniquePredict)) | (uniqueActual.length != uniquePredict.length) | (actual.length != predict.length)) {
			System.err.println("Actual and predict array must have same length or classifications.");
			throw new IllegalArgumentException("Actual and predict array must have same length or classifications.");
		}
		else {
			// Initialize confusion matrix with value of 0
			HashMap<String,Integer> cmatrix = new HashMap<String,Integer>();
			for (String actualClassName : uniqueActual) {
				for (String predictedClassName : uniqueActual) {
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
			for(String predictedClassName : uniqueActual) {
			    System.out.print("\t" + predictedClassName);
			}
			System.out.println();
			
			for(String actualClassName : uniqueActual) {
			    System.out.print(actualClassName);
			    for(String predictedClassName : uniqueActual) {
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
	