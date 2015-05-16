package book;
import pearsonCorrelationCoefficient.PearsonCorrelation;
import distance.*;

public class Recommend {

	LoadFile loadFile;
	PearsonCorrelation
	final static int distanceMode = 1; // 1 for Manhatan and 2 for Eucli
	
	public static void main(String[] args) {
		Recommend rec = new Recommend();
	}
	
	public Recommend(int id) {
		loadFile = new LoadFile();
		loadFile.loadUsers();
		loadFile.loadRating();
		getHigestKCorrelations
	}
	
	
	
	
}
