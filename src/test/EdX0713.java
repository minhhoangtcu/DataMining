package test;
import org.apache.commons.math3.linear.*;

public class EdX0713 {
		
	public static void main(String[] args) {
		double[][] matrix1 = {{1, 2, 3}, {4, 5, 6}};
		double[][] matrix2 = {{1, 2}, {3, 4}, {5, 6}};
		RealMatrix m = MatrixUtils.createRealMatrix(matrix1);
		RealMatrix n = MatrixUtils.createRealMatrix(matrix2);
		RealMatrix p = m.multiply(n);
		double[][] output = p.getData();
		
		for(double[] row : output) {
			for (double i : row) {
	            System.out.print(i);
	            System.out.print("\t");
	        }
	        System.out.println();
        }
	}	
}
