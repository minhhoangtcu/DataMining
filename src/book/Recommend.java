package book;
import pearsonCorrelationCoefficient.PearsonCorrelation;
import distance.*;

public class Recommend {

	LoadFile loadFile;
	PearsonCorrelation correlation;
	final static int distanceMode = 1; // 1 for Manhattan and 2 for Eucliean
	
	public static void main(String[] args) {
		Recommend rec = new Recommend(distanceMode);
	}
	
	public Recommend(int id) {
		loadFile = new LoadFile("book");
		loadFile.initUsers();
		loadFile.initRating();
		correlation = new PearsonCorrelation();
		correlation.getHigestKCorrelations(id, loadFile.people, 3);
	}
}
