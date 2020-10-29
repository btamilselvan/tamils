package com.success.wip.controllers.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

  private DateUtils() {}

  public static String getCurrentDateString() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    return sdf.format(new Date());
  }

  public static String getDateStringFromDate(Date date) {
    if (date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      return sdf.format(date);
    }
    return null;
  }

  public static Date getDateFromString(String source) {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    try {
      return sdf.parse(source);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Date getCurrentDateTime(TimeZone tz) {
    return Date.from(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(tz.getID())).toInstant());
  }

  public static Date getDateMinus(TimeZone tz, int totalDaysToMinus) {
    return Date.from(
        ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(tz.getID()))
            .toInstant()
            .minus(totalDaysToMinus, ChronoUnit.DAYS));
  }

  public static Date plusDays(Date date, int plus) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DAY_OF_MONTH, plus);
    return c.getTime();
  }

  public static void main1(String[] args) {
    System.out.println(Instant.now());
  }
  public static void main(String[] args) {
	
}
}
