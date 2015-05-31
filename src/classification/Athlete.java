package classification;

public class Athlete {
	
	private String name;
	private String classification;
	private int height;
	private int weight;
	
	public Athlete(String name, String classification, int height, int weight) {
		this.name = name;
		this.classification = classification;
		this.height = height;
		this.weight = weight;
	}
	
	public int[] toVector() {
		int[] vector = new int[2];
		vector[0] = height;
		vector[1] = weight;
		return vector;
	}
	
	public String toString() {
		return (name + "\t" + classification + "\t" + height + "\t" + weight);
	}
	
	public String getName() {
		return name;
	}
	
	public String getClassification() {
		return classification;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWeight() {
		return weight;
	}
}
