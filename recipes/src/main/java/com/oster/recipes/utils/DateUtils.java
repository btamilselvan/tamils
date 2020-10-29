package com.oster.recipes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  public static String getCurrentDateString() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    return sdf.format(new Date());
  }
}
