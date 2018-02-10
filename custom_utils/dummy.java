import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class dummy {
  static String num_hour = "([0]?[1-9]|1[0-2])"; // 2 digit clock hour
  static String num_date = "([0]?[1-9]|[1-2][0-9]|3[0-1])"; // date 1 to 31
  static String num_year = "([1-9]{1}[0-9]{3})"; // Year format
  static String day = "(today|tomorrow|sun(day)?|mon(day)?|tue(sday)?|wed(nesday)?|thu(rsday)?|fri(day)?|sat(urday)?)";  //any one day, where (sun == sunday), (mon == monday) so on.
  static String month = "(jan(uary)?|feb(ruary)?|march|april|may|june|july|aug(ust)?|sept(ember)?|oct(ober)?|nov(ember)?|dec(ember)?)";  // any one month, (jan == january) so on.
  static String date1 = num_date + "(th|rd|st|nd)?\\s+" + month;   // date format 1 (NUMBER MONTH) provided in docx: optional suffix to dates,(eg., 1st, 2nd, 3rd) included.
  static String date2 = month + "\\s+" + num_date + "(th|rd|st|nd)?"; // date format 2 (MONTH NUMBER) provided in docx.
  static String date3 = num_date + "(th|rd|st|nd)?\\s+" +month +"\\s+"+num_year; // date format 3 (NUMBER MONTH YEAR) " " "
  static String date4 = num_year + "\\s+" + month + "\\s+" + num_date + "(th|rd|st|nd)?"; // date format 4 ( YEAR MONTH NUMBER)
  static String date = "("+date1+"|"+date2+"|"+date3+"|"+date4+")"; // any one of the date formats date1, date2, date3, date4.
  static String DATE_TIME = "("+day+"|"+date+")?" +"\\s?"+ "("+num_hour+")?" +"\\s?"+ "([ap][\\.]?m)" +"\\s?"+ "("+num_date+"|"+num_hour+")?" +"\\s?" + "("+day+"|"+date+")?";
    public static void main(String[] args) {
      String text    = "Can you tell me when will it rain in Bangalore after 6 a.m. tomorrow";
        String patternString = DATE_TIME;

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        String replaceAll = matcher.replaceAll(" {date_time}");
               System.out.println("replaceAll   = " + replaceAll);
    }
}
