import java.util.*;
import java.lang.*;
import java.io.*;
import custom_utils.*;

class Main {

	public static String convertToGrammer(String st){
		Preprocess pre = new Preprocess();
		Matching ma = new Matching();
		PlaceholderMatch pm = new PlaceholderMatch();
		Concept_parser concept_parser = new Concept_parser();

		st = pre.rExtraChars(st);
		st = pre.rExtraSpaces(st);
		st = ma.find_dateTime(st);
		st = pm.find_placeholder(st);
		st = concept_parser.generate_concept(st);
		st = pre.rExtraSpaces(st);
		return st;
	}
	public static void main(String[] args) {

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
		// 		System.out.println(convertToGrammer(line));
		// 		System.out.println();
		// 		line = reader.readLine();
		// 	}
		// 	reader.close();
		// } catch (Exception e) {
		// 	System.out.println("Error reported" + e);
		// }

		try{
		// GrammarWeight gw = new GrammarWeight(); // generates TF-IDF of every word.
		
		FileRead fr = new FileRead();

		ArrayList<String> al = fr.readFileAlLine(new File("./resources/Testing/input.txt"));
		for(String t: al)
			System.out.println(t);
		}catch(Exception e){
			System.out.println(e);
		}


	}
}
