package classification.general;

public class Item {
	
	private String classification;
	private double[] attributes;
	private String[] ignores;

	public Item(String classification, double[] attributes, String[] ignores) {
		this.classification = classification;
		this.attributes = attributes;
		this.ignores = ignores;
	}
	
	public String toString() {
		String output = "";
		output += classification;
		for (double value: attributes) {
			output = output + "\t" + value;
		}
		for (String value: ignores) {
			output = output + "\t" + value;
		}
		return output;
	}
	
	public String getClassification() {
		return classification;
	}
	
	public double[] getAttributes() {
		return attributes;
	}
	
	public String[] getIgnores() {
		return ignores;
	}
}
