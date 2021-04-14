package dateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;

public class DateFunctions {

	public static String GetTodayDate() {
		// Create Today Date from system date/Time
		// Today Date
		String TodayDateX1 = null;
		Date TodayDate = new Date();
		String TodayDateX = FormatDate(TodayDate, TodayDateX1);

		return TodayDateX;
	}

	public static String GetAdjustedDate(int Days) {
		// Adjust Date by number of days sent in.
		String AdjDateX1 = null;
		Date TodayDate = new Date();
		Date AdjDate = addDays(TodayDate, Days);
		String AdjDateX = FormatDate(AdjDate, AdjDateX1);

		return AdjDateX;
	}

	public static String GetYear(int Years, String InputDate) throws ParseException {
		// Adjust Date by number of days sent in.
		String AdjDateX1 = null;
		Date TDate = new SimpleDateFormat("dd/MM/yyyy").parse(InputDate);
		Date AdjDate = addYears(TDate, Years);
		String AdjDateX = FormatDate(AdjDate, AdjDateX1);

		return AdjDateX;
	}

	public static String GetAdjustedDate_Months(int Months) {
		// Adjust Date by number of Months sent in.
		String AdjDateX1 = null;
		Date TodayDate = new Date();
		Date AdjDate = addMonth(TodayDate, Months);
		String AdjDateX = FormatDate(AdjDate, AdjDateX1);

		return AdjDateX;
	}

	public static String GetAdjustedDate_Years(int Years) {
		// Adjust Date by number of Years sent in.
		String AdjDateX1 = null;
		Date TodayDate = new Date();
		Date AdjDate = addYears(TodayDate, Years);
		String AdjDateX = FormatDate(AdjDate, AdjDateX1);

		return AdjDateX;
	}

	public static String convertToDate_yyyyMMdd(String inputdate) throws ParseException {
		Date TDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputdate);
		String TDate_string = new SimpleDateFormat("yyyy-MM-dd").format(TDate);
		return TDate_string;
	}

	public static String convertToShortDate(String inputdate) throws ParseException {
		Date date_short = new SimpleDateFormat("dd/MM/yy").parse(inputdate);
		String date_shortString = new SimpleDateFormat("dd/MM/yy").format(date_short);
		return date_shortString;
	}

	public static String GetDate_2(int Days, String InputDate) throws ParseException {
		// Adjust Date by number of days sent in.
		String AdjDateX1 = null;
		Date TDate = new SimpleDateFormat("dd/MM/yyyy").parse(InputDate);
		Date AdjDate = addDays(TDate, Days);
		String AdjDateX = FormatDate(AdjDate, AdjDateX1);

		return AdjDateX;
	}
	
	public static String FormatDate(Date date, String FormatDate) {
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		FormatDate = sdf.format(date);
		return FormatDate;
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, +days);
		return cal.getTime();
	}

	public static Date addYears(Date date, int Years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, +Years);
		return cal.getTime();
	}

	public static Date addMonth(Date date, int Months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, +Months);
		return cal.getTime();
	}
	
	public static String getCurrentTime() {
		String currTime = "";
		Date date = new Date();
		String strTimeFormat = "HH:mm";
		DateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
		currTime= timeFormat. format(date);
		return currTime;
	}
	
	public String GetAdjustedDate_Mins(String visitTime, int time) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		java.util.Date d = df.parse(visitTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, time);
		return df.format(cal.getTime());
	}

	public String GetAdjustedDate_Hour(String reviewTime, int i) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		java.util.Date d = df.parse(reviewTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.HOUR_OF_DAY, i);
		return df.format(cal.getTime());
	}

	public static String GetAdjustedCurrentTime_Minutes(int i) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Calendar now = Calendar.getInstance();
	    now.setTime(df.parse(getCurrentTime()));
		now.add(Calendar.MINUTE, i);
	    return df.format(now.getTime());
	}
	
	public static String GetAdjustedCurrentTime_Hours(int i) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Calendar now = Calendar.getInstance();
	    now.setTime(df.parse(getCurrentTime()));
		now.add(Calendar.HOUR_OF_DAY, i);
	    return df.format(now.getTime());
	}
	
//// Below method to be used only for conversion from 12/31/2020 11:59:59 PM to 31/12/2020 23:59 
//	public static String Hours12to24(String input) throws ParseException {
//		String[] dateTime = input.split(" ");
//		String[] date = dateTime[0].split("/");
//		String[] time = dateTime[1].split(":");
//		String input12hr = String.format("%02d", Integer.parseInt(date[0].replaceAll("[^\\d.]*", "")))
//				+ String.format("%02d", Integer.parseInt(date[1].replaceAll("[^\\d.]*", "")))
//				+ date[2].replaceAll("[^\\d.]*", "") + " ";
//		input12hr = input12hr + String.format("%02d", Integer.parseInt(time[0].replaceAll("[^\\d.]*", "")))
//				+ String.format("%02d", Integer.parseInt(time[1].replaceAll("[^\\d.]*", "")))
//				+ String.format("%02d", Integer.parseInt(time[2].replaceAll("[^\\d.]*", ""))) 
//				+ " "+ dateTime[2].replaceAll("[^\\w.]*", "");
//		DateFormat inputFormat = new SimpleDateFormat("MMddyyyy hhmmss aa");
//		// Desired format: 24 hour format: Change the pattern as per the need
//		DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//		// Converting the input String to Date convert format and storing it in String
//		String output = outputformat.format(inputFormat.parse(input12hr));
//		// Return the date
//		return output;
//	}
//	
//	public static String Hours12to24_web(String input) throws ParseException {
//		String[] dateTime = input.split(" ");
//		String[] date = dateTime[0].split("/");
//		String[] time = dateTime[1].split(":");
//		String input12hr = String.format("%02d", Integer.parseInt(date[0].replaceAll("[^\\d.]*", "")))
//				+ String.format("%02d", Integer.parseInt(date[1].replaceAll("[^\\d.]*", "")))
//				+ date[2].replaceAll("[^\\d.]*", "") + " ";
//		input12hr = input12hr + String.format("%02d", Integer.parseInt(time[0].replaceAll("[^\\d.]*", "")))
//				+ String.format("%02d", Integer.parseInt(time[1].replaceAll("[^\\d.]*", "")))
//				+ String.format("%02d", Integer.parseInt(time[2].replaceAll("[^\\d.]*", ""))) ;
////				+ " "+ dateTime[2].replaceAll("[^\\w.]*", "");
//		DateFormat inputFormat = new SimpleDateFormat("ddMMyyyy hhmmss");
//		// Desired format: 24 hour format: Change the pattern as per the need
//		DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//		// Converting the input String to Date convert format and storing it in String
//		String output = outputformat.format(inputFormat.parse(input12hr));
//		// Return the date
//		return output;
//	}
	
	public static String getTheAdjustTimeDateFromConstant(String fieldValue) throws Exception {
		if(fieldValue.matches("TDMinus[0-9YM]+")) {
			if(fieldValue.endsWith("Y"))
				return DateFunctions.GetAdjustedDate_Years(0 - Integer.parseInt(StringUtils.substringBetween(fieldValue, "TDMinus", "Y")));
			else if(fieldValue.endsWith("M"))
				return DateFunctions.GetAdjustedDate_Months(0 - Integer.parseInt(StringUtils.substringBetween(fieldValue, "TDMinus", "M")));
			else
				return DateFunctions.GetAdjustedDate(0 - Integer.parseInt(StringUtils.substringAfter(fieldValue, "TDMinus")));
		}else if(fieldValue.matches("TDPlus[0-9YM]+")) {
			if(fieldValue.endsWith("Y"))
				return DateFunctions.GetAdjustedDate_Years(Integer.parseInt(StringUtils.substringBetween(fieldValue, "TDPlus", "Y")));
			else if(fieldValue.endsWith("M"))
				return DateFunctions.GetAdjustedDate_Months(Integer.parseInt(StringUtils.substringBetween(fieldValue, "TDPlus", "M")));
			else
				return DateFunctions.GetAdjustedDate(Integer.parseInt(StringUtils.substringAfter(fieldValue, "TDPlus")));
		}else if(fieldValue.matches("CTPlus[0-9H]+")) {
			if(fieldValue.endsWith("H"))
				return DateFunctions.GetAdjustedCurrentTime_Hours(Integer.parseInt(StringUtils.substringBetween(fieldValue, "CTPlus", "H")));
			else
				return DateFunctions.GetAdjustedCurrentTime_Minutes(Integer.parseInt(StringUtils.substringAfter(fieldValue, "CTPlus")));
		}else if(fieldValue.matches("CTMinus[0-9H]+")) {
			if(fieldValue.endsWith("H"))
				return DateFunctions.GetAdjustedCurrentTime_Hours(0 - Integer.parseInt(StringUtils.substringBetween(fieldValue, "CTMinus", "H")));
			else
				return DateFunctions.GetAdjustedCurrentTime_Minutes(0 - Integer.parseInt(StringUtils.substringAfter(fieldValue, "CTMinus")));
		}
		else
			return fieldValue;
	}
}
