package com.aj.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	// use yyyy-MM-dd HH:mm:ss format only
	public static Timestamp convertStringToTimestamp(String strDate)  {
		try {
			return new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate).getTime());
		} catch (Exception e) {
			System.out.println("Error while converting String date into Timestamp" + e.getMessage());
		}
		return null; // we can return TimeStamp with todays date to handle failure in better way
	}

	
		public static String convertTimestampToString(Timestamp date)  {
			try {
				date.getTime();
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"). format(new Date(date.getTime()));
			} catch (Exception e) {
				System.out.println("Error while converting String date into Timestamp" + e.getMessage());
			}
			return null; // we can return TimeStamp with todays date to handle failure in better way
		}
}
