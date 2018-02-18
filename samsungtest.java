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

		ArrayList<String> stList = new ArrayList<>();
		// concepts are replaced as stated in Concept/*.txt files
		stList = concept_parser.generate_concept(st);
		System.out.println(stList);
		// DateTime placeholders are detected and replaced
		st = ma.find_dateTime(st);
		// Placeholders(excluding dateTime) are identified and replaced
		st = pm.find_placeholder(st);
		System.out.println("B op: "+st);
		// ----------------------------------------------
		OpenPhrase op = new OpenPhrase(st);
		// ----------------------------------------------
		// Extra spaces are removed.
		st = pre.rExtraSpaces(op.st);
		System.out.println(st);
		// The output(result) in the form of commands(Grammar file names) are returned based on the grammar string obtained from above processes.
		// System.out.println("Grammar:\t"+st); // Uncomment this to see input converted to grammar.
		st = grammarAnalyser.findGrammer(st);
		return st;
	}

	public static void main(String[] args) {

		// Manual Testing - Uncomment the below line to test you own sentences one by one or leave it as such to read from input.txt
		// String st="with name go to make money create a alarm at 8 a.m. tomorrow";
		// String st = "with {name_concept} go to make money {create_concept} an alram for me at <dateTime> thanks";
		// System.out.println(st);
		// System.out.println(convertToGrammer(st));


		// Input from ./resources/Testing/input.txt
		FileRead fileread = new FileRead();
		// System.out.println("*****Please find the output in the Testing folder*****");
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
				// System.out.println(result);
				System.out.println("**************************************************************************************************************************");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Error reported" + e);
		}
	}
}
