package pearsonCorrelationCoefficient;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import distance.Person;

import org.junit.Test;

import book.LoadFile;
import data.Bands;

public class PearsonCorrelationTest {
	
	PearsonCorrelation correlation = new PearsonCorrelation();
	LoadFile load = new LoadFile();
	Person claraP, robertP;

	public void initPeople() {
		Map<String, Double> clara = new HashMap<>();
		clara.put("Blues Traveler", 4.75);
		clara.put("Noral Jones", 4.5);
		clara.put("Phoenix", 5.0);
		clara.put("The Strokes", 4.25);
		clara.put("Weird AI", 4.0);
		claraP = new Person("Clara", clara);

		Map<String, Double> robert = new HashMap<>();
		robert.put("Blues Traveler", 4.0);
		robert.put("Noral Jones", 3.0);
		robert.put("Phoenix", 5.0);
		robert.put("The Strokes", 2.0);
		robert.put("Weird AI", 1.0);
		robertP = new Person("Robert", robert);
	}
	
	@Test
	public void getHigestK() {
		load.initUsers();
		load.initRating();
		correlation.getHigestKCorrelations(1, load.getPeople(), 3);
	}
	
	@Test
	public void sumOfXYTest() {
		initPeople();
		assertEquals(70.0, correlation.getSumOfXY(claraP, robertP), 0.00001);
	}

	@Test
	public void sumOfXTest() {
		initPeople();
		double sumOfX = correlation.getSumOfFirst(claraP, robertP, 1);
		double sumOfY = correlation.getSumOfFirst(robertP, claraP, 1);
		double sumOfXSquare = correlation.getSumOfFirst(claraP, robertP, 2);
		double squareOfSumOfX = Math.pow(correlation.getSumOfFirst(claraP, robertP, 1), 2); 
		assertEquals(22.5, sumOfX, 0.00001);
		assertEquals(15, sumOfY, 0.00001);
		assertEquals(101.875, sumOfXSquare, 0.00001);
		assertEquals(506.25, squareOfSumOfX, 0.00001);
	}
	
	@Test
	public void productOfSumXAndSumYOverNTest() {
		initPeople();
		assertEquals(67.5, correlation.getProductOfSumXAndSumYOverN(claraP, robertP), 0.00001);
		assertEquals(67.5, correlation.getProductOfSumXAndSumYOverN(robertP, claraP), 0.00001);
	}
	
	@Test
	public void correlationTest() {
		initPeople();
		Bands.initMap();
		Bands.initPeople();
		assertEquals(1, correlation.computeCorrelation(claraP, robertP), 0.00001);
		assertEquals(-0.90405, correlation.computeCorrelation(Bands.angelicaP, Bands.billP), 0.00001);
		assertEquals(0.42008, correlation.computeCorrelation(Bands.angelicaP, Bands.haileyP), 0.00001);
		assertEquals(0.76397, correlation.computeCorrelation(Bands.angelicaP, Bands.jordynP), 0.00001);
	}
}

