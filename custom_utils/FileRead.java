package custom_utils;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class FileRead {

  public String[] getFileList(File file){
    String[] paths = file.list();
    return paths;
  }

  public ArrayList<String> readFileAsLine(File file) throws Exception{
     BufferedReader br = new BufferedReader(new FileReader(file));
     String st;
     ArrayList<String> al=new ArrayList<String>();
     while ((st = br.readLine()) != null){
       al.add(st);
     }
      br.close();
     return al;
  }
  
  public ArrayList<String> readFileAsWord(File file) throws Exception{
    BufferedReader br = new BufferedReader(new FileReader(file));
     String st;
     ArrayList<String> al=new ArrayList<String>();
     while ((st = br.readLine()) != null){
      ArrayList<String> words = st.split(" ");
      al.addAll(words);
     }
      br.close();
     return al;
  }
}
