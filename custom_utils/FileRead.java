import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class FileRead {

  public static void main(String[] args) throws Exception{

     // We need to provide file path as the parameter:
     // double backquote is to avoid compiler interpret words
     // like \test as \t (ie. as a escape sequence)
     File file = new File("../resources/Concept/what_concept.txt");

     BufferedReader br = new BufferedReader(new FileReader(file));

     String st;
     while ((st = br.readLine()) != null)
       System.out.println(st);
	}
}
