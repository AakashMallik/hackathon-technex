package custom_utils;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import custom_utils.FileRead.*;

public class GrammarWeight {
  HashMap<String, HashMap<String, HashMap< String, Float >>> datadict = new HashMap<String, HashMap<String, HashMap< String, Float>>>();
  public GrammarWeight() throws Exception{

     // File file = new File("./resources/Concept/what_concept.txt");
     FileRead fr = new FileRead();
     String[] FilesList= fr.getFileList(new File("./resources/Grammar/"));
     // System.out.println(grammarFilesList[0]);
     

   }
  public String getWeight(String file_name){
    return "fdb";
  }
}
