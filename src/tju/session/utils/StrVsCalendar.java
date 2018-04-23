package tju.session.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * Parse a string to calendar
 * @author v-jingfl
 *
 */
public class StrVsCalendar {
	/**
	 * Format: MM/dd/yyyy hh:mm:ss aa  7/1/2012 3:57:46 AM
	 */
	public static final String FORMAT_1 = "MM/dd/yyyy hh:mm:ss aa";
	/**
	 * Format: yyyy/MM/dd HH:mm:ss   
	 */
	public static final String FORMAT_2 = "yyyy/MM/dd HH:mm:ss";
	/**
	 * Format: yyyy/MM/dd HH:mm
	 */
	public static final String FORMAT_3 = "yyyy/MM/dd HH:mm";
	/**
	 * Format: yyyy/MM/dd
	 */
	public static final String FORMAT_4 = "yyyy/MM/dd";	
	/**
	 * Format:MM.dd
	 */
	public static final String FORMAT_5 = "MM.dd";
	/**
	 * Format: yyyy_MM_dd
	 */
	public static final String FORMAT_6 = "yyyy_MM_dd";
	/**
	 * Parse a string to calendar with format_1
	 * @param str
	 * @return
	 */
	public static Calendar parseToCalendar(String str){		
		//String str = "7/2/2012  8:18:00 AM";// "7/2/2012  8:18:00 AM"
		Date time;
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_1,Locale.ENGLISH);
		try {
			time = format.parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			return calendar;
			//System.out.println(calendar);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	/**
	 * parse from string to calendar with a format sFormat
	 * @param str
	 * @param sFormat
	 * @return
	 * @throws ParseException
	 */
	public static Calendar parseToCalendar(String str, String sFormat){
		//String str = "7/2/2012  8:18:00 AM";// "7/2/2012  8:18:00 AM"
		SimpleDateFormat format = new SimpleDateFormat(sFormat);
		Date time;
		try {
			time = format.parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			return calendar;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	/**
	 * parse a calendar object to string
	 * @param date
	 * @return
	 */
	public static String parseToString(Calendar date){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa",Locale.ENGLISH);
		String strDate = sdf.format(date.getTime());
		return strDate;
	}
	/**
	 * parse a calendar to string according to the format.
	 * You can use the StrVsCalendar.FORMAT_1/FORMAT_2/FORMAT_3, as the format.
	 * Or use your own format
	 * @param date
	 * @param format
	 * @return
	 */
	public static String parseToString(Calendar date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.ENGLISH);
		String strDate = sdf.format(date.getTime());
		return strDate;
	}
}
