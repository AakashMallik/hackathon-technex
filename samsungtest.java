import java.util.*;
import java.lang.*;
import java.io.*;
import custom_utils.*;

class samsungtest {

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
		st = grammarAnalyser.findGrammer(st);
		return st;
	}

	public static void main(String[] args) {

		// test script
		FileRead fileread = new FileRead();
		try {
			
			ArrayList<String> sentences = fileread.readFileAsLine(new File("./resources/Testing/input.txt"));

			for(String line : sentences) {
				if (line.subSequence(0, 4).toString().toLowerCase().equals("case")) {
					System.out.println(line);
					continue;
				}
				System.out.println(convertToGrammer(line));
			}
		} catch (Exception e) {
			System.out.println("Error reported" + e);
		}
	}
}
