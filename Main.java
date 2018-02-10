import java.util.*;
import java.lang.*;
import java.io.*;
import custom_utils.*;

class Main {
	public static void main(String[] args) {

		// Concept_parser Concept_parser = new Concept_parser();
		// System.out.println(Concept_parser.generate_concept("Can you tell me when will it rain in Bangalore after 6 a.m. tomorrow"));
		Matching mm = new Matching();
		mm.test();
		// System.out.println(mm.isDateTime("Can you tell me when will it rain in Bangalore after 6 a.m. tomorrow"));
		// String st = "Can you tell me when!   # will it rain. in., Bangalore? after 6 a.m. tomorrow";
		// System.out.println(st);
		// Preprocess pp = new Preprocess();

		// pp.rExtraSpaces( pp.rExtraChars(st));
		// System.out.println(st+"\n"+pp.getIt());

	}
}
