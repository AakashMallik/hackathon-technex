import java.util.*;
import java.lang.*;
import java.io.*;
import custom_utils.*;

class Main {
	public static void main(String[] args) {

		// Concept_parser concept_parser = new Concept_parser();
    //
		// // test script
		// File fileOject = new File("./resources/Testing/input.txt");
		// try {
		// 	BufferedReader reader = new BufferedReader(new FileReader(fileOject));
		// 	String line = reader.readLine();
    //
		// 	while (line != null) {
		// 		if (line.subSequence(0, 4).equals("Case")) {
		// 			line = reader.readLine();
		// 			continue;
		// 		}
		// 		System.out.println(line);
		// 		System.out.println(concept_parser.generate_concept(line));
		// 		System.out.println();
		// 		line = reader.readLine();
		// 	}
		// 	reader.close();
		// } catch (Exception e) {
		// 	System.out.println("Error reported" + e);
		// }
		Matching mm = new Matching();
		// mm.test();
		System.out.println(mm.rPlaceHolder("Set an alarm at 7"));

	}
}
