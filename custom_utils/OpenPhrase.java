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
  HashMap<String, ArrayList<String>> tagmap = new HashMap<String, ArrayList<String>>();
  FileRead fileRead = new FileRead();
  // constructor
  public OpenPhrase(String st){
    this.st = st;
    try {
      ArrayList<String> sentences = fileRead.readFileAsLine(new File("./resources/PlaceHolder/PlaceHolderDetail.txt"));

      for(String line : sentences) {
        switch(line.split("\\s+")[1]){
          case "openphrase":
            System.out.println(line.split("\\s+")[0]);
            openphrase(line.split("\\s+")[0]);
            break;
          case "filelookup":
            // System.out.println(line.split("\\s+")[0]);
            // @TO.DO
            break;
          case "codded":
            // System.out.println(line.split("\\s+")[0]);
            // @TO.DO
            break;
          default:
            System.out.println("\nNo Match check :\\ PlaceHolderDetail.txt for more:\nError in OpenPhrase class > switch statement\n");
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

  private void openphrase(String tag){
    ArrayList<String> fileList = fileRead.getFileList(new File("./resources/Grammar/"));

    try {
      for(String fileName: fileList){
        ArrayList<String> sentences = fileRead.readFileAsLine(new File("./resources/Grammar/"+fileName));
        for(String line : sentences) {
            String[] words = line.split("\\s+");
            if(matchMe(".*"+tag+".*",line)){
              int index = Arrays.asList(words).indexOf(tag);
              // System.out.println(tag+" -> "+line+" "+Integer.valueOf(index));
              String pattern = "";
              for(int i=0;i<words.length;i++){
                if( i >= index - this.windowSize && i < index)
                  pattern += words[i].replaceAll("\\{","\\\\\\{").replaceAll("\\}","\\\\\\}");
                else if( i == index)
                  pattern += ".*";
                else if(i > index && i <= index + this.windowSize)
                  pattern += words[i].replaceAll("\\{","\\\\\\{").replaceAll("\\}","\\\\\\}");
              }

              // String pattern = words[Math.max(index-1,0)].replaceAll("\\{","\\\\\\{").replaceAll("//}","//////}") +
              // ".*" +
              // words[Math.min(words.length-1,index+1)].replaceAll("\\{","\\\\\\{").replaceAll("//}","//////}");
              System.out.println("Pat: "+pattern);
              boolean match = matchMe(".*" + pattern + ".*", this.st);
              // System.out.println(words[Math.max(index-1,0)]+" * "+words[Math.min(words.length-1,index+1)]+"\t\t\t"+match);
              if(match) {
                pattern = replace(pattern, "\\{[0-9a-zA-Z]+(_){0,1}[0-9a-zA-Z]{0,100}\\}"," ");
                this.st = replace(this.st, pattern, tag);
                System.out.println(this.st);
              }
            }
        }

      }
    } catch (Exception e) {
      System.out.println("Error reported" + e);
    }
  }

  // ************** IGNORE BELOW LINE *********
  // dateTime
  // day
  // date
  // number
  // alarm_name
  // note_name
  // note_content
  // src_event_name
  // tgt_evemt_name
/****************************
 Note: r for replace,
 and change the return type accordingly
***************************/
  // @TO.DO
  public void getAlarmName(String st){

  }
  public void rAlarmName(String st){

  }


  // @TO.DO
  public void getNoteName(String st){

  }
  public void rNoteName(String st){

  }

  // @TO.DO
  public void getNoteContent(String st){

  }
  public void rNoteContent(String st){

  }

  // @TO.DO
  public void getSrcEventName(String st){

  }
  public void rSrcEventName(String st){

  }

  // @TO.DO
  public void getTgtEventName(String st){

  }
  public void rTgtEventName(String st){

  }
}
