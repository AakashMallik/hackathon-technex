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

		ArrayList<String> processed_string_list = new ArrayList<>();
		for (String pt : stList) {
			// DateTime placeholders are detected and replaced
			st = ma.find_dateTime(pt);
			HashMap<String, Set<String>> datetimeMap = ma.map;
			for (String each: datetimeMap.keySet()) {
				for (String item : datetimeMap.get(each)) {
					System.out.println(each+" : "+item );
				}
			}


			// Placeholders(excluding dateTime) are identified and replaced
			st = pm.find_placeholder(st);
			OpenPhrase op = new OpenPhrase(st);
			HashMap<String, ArrayList<String>> tagmap = op.tagmap;
			for (String each:tagmap.keySet() ) {
				for (String item: tagmap.get(each)) {
					System.out.println(each+" : "+item);
				}
			}
			// Extra spaces are removed.
			st = pre.rExtraSpaces(op.st);
			// The output(result) in the form of commands(Grammar file names) are returned based on the grammar string obtained from above processes.
			processed_string_list.add(st);
		}
		System.out.println(processed_string_list);

		st = grammarAnalyser.findGrammer(processed_string_list);
		// System.out.println(st);
		return st;

	}

	public static void main(String[] args) {

		// Manual Testing - Uncomment the below line to test you own sentences one by one or leave it as such to read from input.txt
		// System.out.println(convertToGrammer("Can you please set an alarm at 3 pm tomorrow"));
		// System.out.println(convertToGrammer("make event at midnight and make alarm at 2:30pm"));

		// Input from ./resources/Testing/input.txt
		FileRead fileread = new FileRead();
		// System.out.println("*****Please find the output in the Testing folder*****");
		try {
			PrintWriter writer = new PrintWriter("./resources/Testing/output.txt", "UTF-8");
			ArrayList<String> sentences = fileread.readFileAsLine(new File("./resources/Testing/input.txt"));

			for (String line : sentences) {
				if (line.subSequence(0, 4).toString().toLowerCase().equals("case")) {
					System.out.println(line);
					writer.println(line);
					continue;
				}
				String result = convertToGrammer(line);
				writer.println(result);
				System.out.println(line);
				System.out.println(result);
				System.out.println(
						"**************************************************************************************************************************");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Error reported" + e);
		}
	}
}
