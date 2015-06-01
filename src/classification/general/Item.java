package classification.general;

import java.util.Arrays;

public class Item {
	
	private String classification;
	private double[] attributes;
	private double[] normalizedAttributes;
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
	
	public boolean equals(Item otherItem) {
		boolean isSameClassification = classification.equals(otherItem.classification);
		boolean isSameAttributes = Arrays.equals(attributes, otherItem.attributes);
		boolean isSameIgnores = Arrays.equals(ignores, otherItem.ignores);
		return (isSameClassification & isSameAttributes & isSameIgnores);
	}
	
	public String getClassification() {
		return classification;
	}
	
	public double[] getAttributes() {
		return attributes;
	}
	
	public void setNormalizedAttributes(double[] normalizedAttributes) {
		this.normalizedAttributes = normalizedAttributes;
	}
	
	public double[] getNormalizedAttributes() {
		return normalizedAttributes;
	}
	
	public String[] getIgnores() {
		return ignores;
	}
}
