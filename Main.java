import java.util.*;
import java.lang.*;
import java.io.*;
import custom_utils.*;

class Main {

	public static String convertToGrammer(String st) {
		Preprocess pre = new Preprocess();
		DateTimePlaceholder ma = new DateTimePlaceholder();
		PlaceholderMatch pm = new PlaceholderMatch();
		Concept_parser concept_parser = new Concept_parser();
		GrammarAnalyser grammarAnalyser = new GrammarAnalyser();

		st = pre.rExtraChars(st);
		st = pre.rExtraSpaces(st);
		st = concept_parser.generate_concept(st);
		st = ma.find_dateTime(st);
		st = pm.find_placeholder(st);
		st = pre.rExtraSpaces(st);
		System.out.println("Conv: " + st);
		st = grammarAnalyser.findGrammer(st);
		return st;
	}

	public static void main(String[] args) {

		// test script
		File fileOject = new File("./resources/Testing/input.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileOject));
			String line = reader.readLine();

			while (line != null) {
				if (line.subSequence(0, 4).equals("Case")) {
					line = reader.readLine();
					continue;
				}
				System.out.println(line);
				System.out.println(convertToGrammer(line));
				System.out.println();
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Error reported" + e);
		}

		// try {
		// 	GrammarWeight gw = new GrammarWeight();
		// 	HashMap<String, Double> idf = gw.getIDF(); // generates IDF of everyword.
		// 	for (String ss : idf.keySet())
		// 		System.out.println(ss + " " + String.valueOf(idf.get(ss)));
		// } catch (Exception e) {
		// 	System.out.println(e);
		// }
		// try{
		// 	GrammarWeight gw = new GrammarWeight();
		// 	HashMap<String, HashMap<String, Double>> tf = gw.getTF(); // generate TF of every word.
		// 	HashMap<String, HashMap<String, String>> charMap = gw.getSymbol();
		// 	for(String ss: tf.keySet()){
		// 		System.out.println(ss);
		// 		for(String word : tf.get(ss).keySet()){
		// 			System.out.println(word+" "+charMap.get(ss).get(word)+" "+String.valueOf(tf.get(ss).get(word)));
		// 			}
		// 		System.out.println();
		// 		}
		// 	}catch(Exception e){
		// 		System.out.println(e);
		// 	}
		// System.out.println(convertToGrammer("Can you book a flight ticket on 14 February from Bangalore to Varanasi"));
	}
}
