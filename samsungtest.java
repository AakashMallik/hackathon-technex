import java.util.*;
import java.lang.*;
import java.io.*;
import custom_utils.*;

class samsungtest {

	public static Set<String> outputAttr;
	public static String convertToGrammer(String st) {
		Preprocess pre = new Preprocess();
		DateTimePlaceholder ma = new DateTimePlaceholder();
		PlaceholderMatch pm = new PlaceholderMatch();
		Concept_parser concept_parser = new Concept_parser();
		GrammarAnalyser grammarAnalyser = new GrammarAnalyser();


		st = pre.rExtraChars(st);

		st = pre.rExtraSpaces(st);

		String st_orig = st;

		ArrayList<String> stList = new ArrayList<>();

		stList = concept_parser.generate_concept(st);

		ArrayList<String> processed_string_list = new ArrayList<>();
		outputAttr = new HashSet<>();
		for (String pt : stList) {

			st = ma.find_dateTime(pt);
			HashMap<String, Set<String>> datetimeMap = ma.map;
			for (String each: datetimeMap.keySet()) {
				for (String item : datetimeMap.get(each)) {

					outputAttr.add(each+" : "+item);
				}
			}

			st = pm.find_placeholder(st);
			for(String each : pm.all_replacements.keySet()){
					for(String item: pm.all_replacements.get(each)){

						outputAttr.add(each+" : "+item);
					}
			}


			OpenPhrase op = new OpenPhrase(st);
			HashMap<String, ArrayList<String>> tagmap = op.tagmap;
			for (String each:tagmap.keySet() ) {
				for (String item: tagmap.get(each)) {

					outputAttr.add(each+" : "+item);
				}
			}

			st = pre.rExtraSpaces(op.st);

			processed_string_list.add(st);
		}


		st = grammarAnalyser.findGrammer(processed_string_list);

		System.out.println();

		return st;

	}

	public static void main(String[] args) {






		FileRead fileread = new FileRead();
		
		try {
			PrintWriter writer = new PrintWriter("./resources/Testing/output.txt", "UTF-8");
			ArrayList<String> sentences = fileread.readFileAsLine(new File("./resources/Testing/input.txt"));

			for (String line : sentences) {
				if (line.subSequence(0, 4).toString().toLowerCase().equals("case")) {
					System.out.println("w: "+line);
					writer.println(line);
					continue;
				}
				String result = convertToGrammer(line);
				writer.println(result);

				System.out.println("w: "+result);
				for (String each: outputAttr) {
					writer.println(each);
					System.out.println("w: "+each);
				}


			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Error reported" + e);
		}
	}
}
