package test;
import org.apache.commons.math3.ml.distance.*;

public class EdXExercise {
	
	public static void main(String[] args) {
		EuclideanDistance distance = new EuclideanDistance();
		double[] godFather = {0,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0};
		double[] titanic = {0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0};
		double EuclidDistance = distance.compute(godFather, titanic);
		System.out.println(EuclidDistance);
	}
}
