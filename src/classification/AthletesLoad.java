package classification;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AthletesLoad {
	
	static final String TRAINING = "data\\athletesTrainingSet.txt";
	static final String TEST = "data\\athletesTestSet.txt";
	
	/* UNCOMMENT FOR TEST
	public static void main(String[] args) {
		AthletesLoad load = new AthletesLoad();
		for (Athlete currentA: load.loadFile(TRAINING)) {
			System.out.println(currentA);
		}
	}
	*/

	public Athlete[] loadFile(String fileDir) {
		BufferedReader reader = null;
		ArrayList<Athlete> tempList = new ArrayList<Athlete>();
		
		try {
			reader = new BufferedReader(new FileReader(fileDir));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\t");
				String name = fields[0];
				String classification = fields[1];
				int height = Integer.parseInt(fields[2]);
				int weight = Integer.parseInt(fields[3]);
				tempList.add(new Athlete(name, classification, height, weight));
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Cannot find traning file");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.err.println("File corrupted");
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.err.println("Cannot close file");
				e.printStackTrace();
			}
		}
		
		return tempList.toArray(new Athlete[tempList.size()]);
	}
	
}
