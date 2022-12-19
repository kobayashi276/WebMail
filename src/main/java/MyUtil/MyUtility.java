package MyUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

public class MyUtility {

    public static void print(Object data){
        System.out.println(data.toString());
    }
    public static void print(Object data, String label){
        System.out.println("---> "+label);
        System.out.println(data.toString());
    }

    public static String getCurrentTime(){
        String time = LocalTime.now().toString();
        return time;
    }
    public static String getCurrentDate(){
        String date = LocalDate.now().toString();
        return date;
    }

    public static String getTimeZone(){
        Calendar now = Calendar.getInstance();
        TimeZone timeZone = now.getTimeZone();
        String result = timeZone.getDisplayName();
        return result;
    }
}
