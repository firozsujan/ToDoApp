package com.task.todoapp.frontend;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * This class is for Common utilities of Frontend
 */
public class UtilitiesFE {
	/**
	 * Convert Dong to localDate Zone Asia/Dhaka
	 * @param longDate
	 * @return
	 */
	public static LocalDate longToLocalDateConversion(Long longDate) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(longDate),ZoneId.of("Asia/Dhaka")).toLocalDate();
	}

	/**
	 * Convert date long to String. Date format is dd-MM-yyyy
	 * @param longDate
	 * @return
	 */
	public static String longToFormattedLocalDateConversion(Long longDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = longToLocalDateConversion(longDate);
		
		
		return localDate.format(formatter);
	}

	/**
	 * convert localDate(DatePicker) to long (time in millisecond)
	 * @param localDate
	 * @return
	 */
	public static Long localDateToLongConversion(LocalDate localDate) {
		System.out.println("localDate: "+localDate);
		if (localDate != null)
			return Timestamp.valueOf(localDate.atStartOfDay()).getTime();
		return null;
	}	
}
