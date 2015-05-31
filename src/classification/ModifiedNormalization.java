package classification;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class ModifiedNormalization {
	
	public double getMedian(int[] input) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for (int value: input) {
			stats.addValue((double) value);
		}
		return stats.getPercentile(50);
	}
	
	public double getAbsoluteSD(int[] input) {
		double sum = 0;
		double median = getMedian(input);
		for (int value: input) {
			sum += Math.abs(value - median);
		}
		double absoluteSD = sum / input.length;
		return absoluteSD;
	}
	
	public double[] getModifiedNormalization(int[] input) {
		double median = getMedian(input);
		double absoluteSD = getAbsoluteSD(input);
		
		double[] normalizedScores = new double[input.length];
		for (int i = 0; i < input.length; i++) {
			double normalized = (input[i] - median) / absoluteSD;
			normalizedScores[i] = normalized;
		}
		
		return normalizedScores;
	}
}
