package book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileConverter {
	
	public static void main(String[] args) {
		FileConverter convert = new FileConverter();
		try {
			convert.convert("data\\BX-Book-Ratings.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Conversion completed");
	}
	
	
	public void convert(String input) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		BufferedWriter writer = new BufferedWriter(new FileWriter(input+"converted"));
		String line;
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("\"", "");
			writer.write(line);
			writer.newLine();
		}
		reader.close();
		writer.close();
	}

}
