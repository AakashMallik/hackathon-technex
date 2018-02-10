package custom_utils;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Preprocess {

  public String rExtraChars(String st){
      String patternString = "[^a-zA-Z\\d\\s:]";
      Pattern pattern = Pattern.compile(patternString);
      Matcher matcher = pattern.matcher(st);
      st = matcher.replaceAll("");
      // System.out.println(st);
      return st;
  }
  public String rExtraSpaces(String x){
    String patternString = "\\s+";
    Pattern pattern = Pattern.compile(patternString);
    Matcher matcher = pattern.matcher(x);
    x = matcher.replaceAll(" ");
    // System.out.println(x);
    return x;
  }
  public String allLower(String st){
      return st.toLowerCase();
  }

}
