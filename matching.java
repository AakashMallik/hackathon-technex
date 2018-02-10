import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class matching {

  static String num_hour = "([0]?[1-9]|1[0-2])";  // 2 digit clock hour
  static String num_date = "([0]?[1-9]|[1-2][0-9]|3[0-1])"; // date 1 to 31
  static String num_year = "([1-9]{1}[0-9]{3})"; // Year format
  static String day = "(today|tomorrow|sun(day)?|mon(day)?|tue(sday)?|wed(nesday)?|thu(rsday)?|fri(day)?|sat(urday)?)";  //any one day, where (sun == sunday), (mon == monday) so on.
  static String month = "(jan(uary)?|feb(ruary)?|march|april|may|june|july|aug(ust)?|sept(ember)?|oct(ober)?|nov(ember)?|dec(ember)?)";  // any one month, (jan == january) so on.
  static String date1 = num_date + "(th|rd|st|nd)?\\s+" + month;   // date format 1 (NUMBER MONTH) provided in docx: optional suffix to dates,(eg., 1st, 2nd, 3rd) included.
  static String date2 = month + "\\s+" + num_date + "(th|rd|st|nd)?"; // date format 2 (MONTH NUMBER) provided in docx.
  static String date3 = num_date + "(th|rd|st|nd)?\\s+" +month +"\\s+"+num_year; // date format 3 (NUMBER MONTH YEAR) " " "
  static String date4 = num_year + "\\s+" + month + "\\s+" + num_date + "(th|rd|st|nd)?"; // date format 4 ( YEAR MONTH NUMBER)
  static String date = "("+date1+"|"+date2+"|"+date3+"|"+date4+")"; // any one of the date formats date1, date2, date3, date4.


  // pattern matching function to find if the given pattern contains in a string(sentence).
  static boolean matchMe(String pattern, String st){
    Pattern re = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
    Matcher m = re.matcher(st);
    boolean b = m.lookingAt();
    return b;
  }

  // matching for DateTime Placeholder
  static boolean isDateTime(String st){
    String DATE_TIME = "("+day+"|"+date+")?" +"\\s?"+ "("+num_hour+")?" +"\\s?"+ "([ap][\\.]?m)" +"\\s?"+ "("+num_date+"|"+num_hour+")?" +"\\s?" + "("+day+"|"+date+")?";
    return matchMe(DATE_TIME,st);
  }
  // matching for day, today, tomorrow, sunday, mon etc.
  static boolean isDay(String st){ return matchMe(day,st); }

  // matching for any of the date format already given, date1,date2,date3,date4
  static boolean isDate(String st){ return matchMe(date, st); }

  // matching for <Number> whose range varies from 1-31
  static boolean isNum(String st){ return matchMe(num_date, st); }

  static void callme(String st){
    boolean result = isDay(st);
    System.out.println(st+" -> "+String.valueOf(result));
  }

  static void TestDay(){
    callme("jan"); callme("fgbgf january fgbfg"); callme("hfdhg 89789 feb dhf"); callme("2018 December 31st");
    callme("Mon"); callme("tuesday");  callme("tomorrow");  callme("friday");
  }

  static void TestDate(){
    callme("5th januray"); callme("31 oct 2019"); callme("2018 jan 31st");
  }

  static void Untitled(){
    callme("am"); callme("p.m"); callme("a.m"); callme("PM 3");
    callme("9 pm");
    callme("19 pm tomorrow");
    callme("a.m 10 today");
    callme("SunDay 8 a.m");
    callme("Fri pm 3");
    callme("11 am 7th Jan");
    callme("P.m 06 March 20th");
    callme("05 A.M 2018 Dec 9th");
    callme("14th february 2018 2 am");
    callme("32th 2018 february 2 am");

  }
  public static void main(String[] args) {
    Untitled();
    TestDay();
    TestDate();
  }
}
