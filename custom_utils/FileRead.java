package custom_utils;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class FileRead {
  public ArrayList<String> convertToArrayList(String[] ss){
    ArrayList<String> paths = new ArrayList<>();
    for(String i: ss)
      paths.add(i);
    return paths;
  }
  public ArrayList<String> getFileList(File file){
    return convertToArrayList(file.list());
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
      ArrayList<String> words = convertToArrayList(st.split(" "));
      al.addAll(words);
     }
      br.close();
     return al;
  }
}
// def inverse_document_frequencies(tokenized_documents):
//     idf_values = {}
//     all_tokens_set = set([item for sublist in tokenized_documents for item in sublist])
//     for tkn in all_tokens_set:
//         contains_token = map(lambda doc: tkn in doc, tokenized_documents)
//         idf_values[tkn] = 1 + math.log(len(tokenized_documents)/(sum(contains_token)))
// return idf_values