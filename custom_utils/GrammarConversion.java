package custom_utils;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class GrammarConversion {

  public void fun() throws Exception{
    // System.out.println("");
     // We need to provide file path as the parameter:
     // double backquote is to avoid compiler interpret words
     // like \test as \t (ie. as a escape sequence)
     String pp = "../resources/Concept/what_concept.txt";
     File file = new File("./resources/Concept/what_concept.txt");

     BufferedReader br = new BufferedReader(new FileReader(file));

     String st;
     while ((st = br.readLine()) != null)
       System.out.println(st);
	}
}
