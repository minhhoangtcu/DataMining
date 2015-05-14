package book;

public class Recommend {

	LoadFile loadFile;
	
	public static void main(String[] args) {
		Recommend rec = new Recommend();
	}
	
	public Recommend() {
		loadFile = new LoadFile();
		loadFile.loadUsers();
		loadFile.loadRating();
	}

	
	
}
