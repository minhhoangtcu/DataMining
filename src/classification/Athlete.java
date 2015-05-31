package classification;

public class Athlete {
	
	private String name;
	private String classification;
	private int height;
	private int weight;
	private double normalizedHeight;
	private double normalizedWeight;
	
	public Athlete(String name, String classification, int height, int weight) {
		this.name = name;
		this.classification = classification;
		this.height = height;
		this.weight = weight;
	}
	
	public double[] toVector() {
		double[] vector = new double[2];
		vector[0] = height;
		vector[1] = weight;
		return vector;
	}
	
	public double[] toNormalizedVector() {
		double[] vector = new double[2];
		vector[0] = normalizedHeight;
		vector[1] = normalizedWeight;
		return vector;
	}
	
	public double getNormalizedHeight() {
		return normalizedHeight;
	}
	
	public double getNormalizedWeight() {
		return normalizedWeight;
	}
	
	public void setNormalizedHeight(double normalizedHeight) {
		this.normalizedHeight = normalizedHeight;
	}
	
	public void setNormalizedWeight(double normalizedWeight) {
		this.normalizedWeight = normalizedWeight;
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
