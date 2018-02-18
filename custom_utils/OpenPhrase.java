package custom_utils;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.Arrays.*;
import java.util.regex.*;

public class OpenPhrase {
  public String st;
  public int windowSize = 1;
  public HashMap<String, ArrayList<String>> tagmap = new HashMap<String, ArrayList<String>>();
  FileRead fileRead = new FileRead();


  public OpenPhrase(String st) {
    this.st = st;
    try {
      ArrayList<String> sentences = fileRead.readFileAsLine(new File("./resources/PlaceHolder/PlaceHolderDetail.txt"));

      for (String line : sentences) {
        switch (line.split("\\s+")[1]) {
        case "openphrase":

          openphrase(line.split("\\s+")[0]);
          break;
        case "filelookup":


          break;
        case "codded":
          

          break;
        default:
          System.out.println(
              "\nNo Match check :\\ PlaceHolderDetail.txt for more:\nError in OpenPhrase class > switch statement\n");
        }
      }
    } catch (Exception e) {
      System.out.println("Error reported" + e);
    }
  }

  private boolean matchMe(String pattern, String st) {
    Pattern re = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    Matcher m = re.matcher(st);
    boolean b = m.lookingAt();
    return b;
  }

  String replace(String st, String pat, String placeHolder) {
    Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(st);
    st = matcher.replaceAll(" " + placeHolder + " ");
    return st;
  }

  private void openphrase(String tag) {
    ArrayList<String> fileList = fileRead.getFileList(new File("./resources/Grammar/"));
    tagmap.put(tag, new ArrayList<String>());
    try {

      for(String fileName: fileList){
        ArrayList<String> sentences = fileRead.readFileAsLine(new File("./resources/Grammar/"+fileName));
        for(String line : sentences) {
            String[] words = line.split("\\s+");
            if(matchMe(".*"+tag+".*",line)){
              int index = Arrays.asList(words).indexOf(tag);
              if(tag.equals("<alarm_name>") && index != words.length-1)
                this.windowSize = 1;
              else
                this.windowSize = 2;

              String pattern = ""; String st2 = "";
              for(int i=0;i<words.length;i++){
                if( i >= index - this.windowSize && i < index){
                  pattern += words[i].replaceAll("\\{","#").replaceAll("\\}","@")+" ";
                  st2 += words[i]+" ";
                }
                else if( i == index){
                  pattern = pattern.trim();
                  pattern += ".*";
                  st2 += words[i]+" ";
                }
                else if(i > index && i <= index + this.windowSize){
                  pattern += words[i].replaceAll("\\{","#").replaceAll("\\}","@")+" ";
                  st2 += words[i]+" ";
                }
              }
              st2 = st2.trim();
              pattern = pattern.trim();




              boolean match = matchMe(".*" + pattern + ".*", this.st.replaceAll("\\{","#").replaceAll("\\}","@"));




              if(match) {

                String[] temp = this.st.split(" ", 0);

                this.st = replace(" "+this.st+" ", "\\s+"+pattern+"\\s+", st2);
                this.st = this.st.trim();


                String[] temp2 = this.st.split(" ", 0);
                String repl = "";
                for (int i = 0; i < temp2.length; i++) {
                  if(!temp2[i].equals(temp[i])){
                    for(int j = i; j < temp.length; j++)  {
                      repl += temp[j] + " ";

                      if (i == temp2.length - 1) {
                        continue;
                      }
                      if (i == temp2.length - 2) {
                        if (temp[j + 1].equals(temp2[i + 1])) {
                          break;
                        }
                        else
                          continue;
                      }
                      if (this.windowSize > 1) {
                        if (temp[j + 1].equals(temp2[i + 1]) && temp[j + 2].equals(temp2[i + 2])) {
                        break;
                      }
                      }


                    }
                    break;
                  }
                }

                ArrayList<String> temp_list = tagmap.get(tag);
                temp_list.add(repl.trim());
                tagmap.put(tag, temp_list);

              }

            }
          }
        }
    } catch (Exception e) {
      System.out.println("Error reported" + e);
    }
  }











  /****************************
   Note: r for replace,
   and change the return type accordingly
  ***************************/












































}
