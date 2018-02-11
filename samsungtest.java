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

		//  Non-Alphanumeric characters are removed
		st = pre.rExtraChars(st);
		// Extra spaces are removed 
		st = pre.rExtraSpaces(st);
		// concepts are replaced as stated in Concept/*.txt files
		st = concept_parser.generate_concept(st);
		// DateTime placeholders are detected and replaced
		st = ma.find_dateTime(st);
		// Placeholders(excluding dateTime) are identified and replaced
		st = pm.find_placeholder(st);
		// Extra spaces are removed.
		st = pre.rExtraSpaces(st);
		// The output(result) in the form of commands(Grammar file names) are returned based on the grammar string obtained from above processes.
		// System.out.println("Grammar:\t"+st); // Uncomment this to see input converted to grammar.
		st = grammarAnalyser.findGrammer(st);
		return st;
	}

	public static void main(String[] args) {

		// Manual Testing - Uncomment the below line to test you own sentences one by one or leave it as such to read from input.txt 
		// System.out.println(convertToGrammer("Can you please set an alarm at 3 pm tomorrow"));
		

		// Input from ./resources/Testing/input.txt
		FileRead fileread = new FileRead();
		System.out.println("*****Please find the output in the Testing folder*****");
		try {
			PrintWriter writer = new PrintWriter("./resources/Testing/output.txt", "UTF-8");
			ArrayList<String> sentences = fileread.readFileAsLine(new File("./resources/Testing/input.txt"));

			for(String line : sentences) {
				if (line.subSequence(0, 4).toString().toLowerCase().equals("case")) {
					System.out.println(line);
					writer.println(line);
					continue;
				}
				String result = convertToGrammer(line);   
				writer.println(result);
				System.out.println(line);
				System.out.println(result);
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Error reported" + e);
		}
	}
}
