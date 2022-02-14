package com.success.hackerrank.medium;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExDeleteRepeatedWords {

  public static void main(String[] args) {

    String regex = "\\b(\\w+)(?:\\W+\\1\\b)+";
    /*The details of the above regular expression can be understood as:
        “\\b”: A word boundary. Boundaries are needed for special cases. For example, in “My thesis is great”, “is” wont be matched twice.
        “\\w+” A word character: [a-zA-Z_0-9]

        “\\W+”: A non-word character: [^\w]

        “\\1”: Matches whatever was matched in the 1st group of parentheses, which in this case is the (\w+)

        “+”: Match whatever it’s placed after 1 or more times
        "?:  A non-capturing group is just that -- don't capture the submatch. The group(int) method doesn't return submatches 
        from non-capturing groups. Generally, if you don't need the value of a submatch, the group should be non-capturing,
         as there is no reason for the regex engine to collect the data for you, if you don't intend to use it.
        */
    Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    Scanner in = new Scanner(System.in);
    int numSentences = Integer.parseInt(in.nextLine());

    while (numSentences-- > 0) {
      String input = in.nextLine();

      Matcher m = p.matcher(input);

      // Check for subsequences of input that match the compiled pattern
      while (m.find()) {
        input = input.replaceAll(m.group(), m.group(1));
      }

      // Prints the modified sentence.
      System.out.println(input);
    }

    in.close();
  }
}
