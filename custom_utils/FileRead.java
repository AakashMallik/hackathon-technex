package custom_utils;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class FileRead {
  public ArrayList<String> convertToArrayList(String[] ss) {
    ArrayList<String> paths = new ArrayList<>();
    for (String i : ss)
      paths.add(i);
    return paths;
  }

  public ArrayList<String> getFileList(File file) {
    return convertToArrayList(file.list());
  }

  public ArrayList<String> readFileAsLine(File file) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String st;
    ArrayList<String> al = new ArrayList<String>();
    while ((st = br.readLine()) != null) {
      st = st.toLowerCase();
      al.add(st);
    }
    br.close();
    return al;
  }

  public ArrayList<String> readFileAsWord(File file) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String st;
    ArrayList<String> al = new ArrayList<String>();
    while ((st = br.readLine()) != null) {
      st = st.toLowerCase();
      ArrayList<String> words = convertToArrayList(st.split(" "));
      al.addAll(words);
    }
    br.close();
    return al;
  }
}