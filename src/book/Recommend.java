package book;
import distance.*;

public class Recommend {

	LoadFile loadFile;
	final static int distanceMode = 1; // 1 for Manhatan and 2 for Eucli
	
	public static void main(String[] args) {
		Recommend rec = new Recommend();
	}
	
	public Recommend() {
		loadFile = new LoadFile();
		loadFile.loadUsers();
		loadFile.loadRating();
	}
	
	
	
	
}
