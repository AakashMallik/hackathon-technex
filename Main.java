import java.util.*;
import java.lang.*;
import java.io.*;
import custom_utils.*;

class Main {
	public static void main(String[] args) {
	// 	FileRead ff = new FileRead();
	// 	try{
	// 	ff.fun();
	// }catch(Exception e){
	// 	System.out.println(e);
	// }
		Concept_parser Concept_parser = new Concept_parser();
		System.out.println(Concept_parser.generate_concept("Can you tell me when will it rain in Bangalore after 6 a.m. tomorrow"));
		// Matching mm = new Matching();
		// mm.test();
		// mm.isDateTime("dfbd dfh");

	}
}
