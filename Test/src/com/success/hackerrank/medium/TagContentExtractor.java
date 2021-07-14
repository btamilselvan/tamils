package com.success.hackerrank.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagContentExtractor {

  /*
   * inputs
   * 
   * <h1>hello</h1>
   * <h1>hello</H1>
   * <h1>hello<h1>
   * <h1>hello</h1><h2>world</h2>
   * <h1>hello</h1><h2>world</h2><>test</>
   * <h1>hello</h1><<<<<
   * <>hello</><>world</>
   * <>hello</><h1>world</h1>
   * <<<<<<
   * >>>>
   * <<>>
   *
   */
  private static Pattern startTag = Pattern.compile("\\<(?!/)(.*?)\\>");
  private static Pattern endTag = Pattern.compile("\\</(.*?)\\>");

  private static void compute(List<String> lines) {
    for (String line : lines) {
      if (!extract(line, false)) {
        // if a line contains all invalid content then print None..
        System.out.println("None");
      }
    }
  }

  private static boolean extract(String line, boolean nested) {
    boolean flag = false;
    Matcher startMatcher = startTag.matcher(line);
    //    System.out.println("line is " + line);
    if (startMatcher.find()) {
      String st = startMatcher.group(1);
      if (line.contains("</" + st + ">")) {
        // matching end tag present
        // extract contents b/w start and end tag
        String contents =
            line.substring(
                line.indexOf("<" + st + ">") + st.length() + 2, line.lastIndexOf("</" + st + ">"));

        if (!st.contentEquals("") && !contents.contentEquals("")) {
          // check the content b/w start and end tags
          // e.g. <h1>hello</h1>
          flag = extract(contents, true);
        }
        // check the remaining part
        // e.g. <h1>hello</h1><h2>world</h2> -> here check the remaining part which is
        // <h2>world</h2>
        line = line.substring(line.lastIndexOf("</" + st + ">") + st.length() + 3);
        if (!line.contentEquals("")) {
          /*
           * treat like this as a new line .. if a line contains at least one of the valid tag then do not print 'None' for the rest of the invalid part.
           * if a line contains all invalid content then print None..
           */
          flag = extract(line, false) || flag;
        }
      } else {
        // no end tag present. check the remaining text.. for e.g. <h1>hello<h1><h2>world</h2> ->
        // remaining part will be hello<h1><h2>world</h2>
        // extract contents b/w start and end tag
        line = line.substring(line.indexOf("<" + st + ">") + st.length() + 2);
        if (line != null && !line.contentEquals("")) {
          flag = extract(line, false) || flag;
        }
      }
    } else if (nested && !"".contentEquals(line)) {
      // this is text either in b/w start and end tag or content something like hello</h1> (from
      // <H1>hello</h1>)
      Matcher endMathcer = endTag.matcher(line);
      if (!endMathcer.find()) {
        // do not print content without matching start tag.
        // no end end tag present. just print the line
        System.out.println(line);
        return true;
      }
    }
    return flag;
  }

  public static void main(String[] args) throws IOException {
    System.out.println("Enter input");
    System.out.println(
        "The first line of input contains a single integer, N (the number of lines)");
    System.out.println("The N subsequent lines each contain a line of text");
    System.out.println("==============================");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line = null;
    int numberOfLines = 0;
    int count = 0;
    List<String> lines = new ArrayList<>();
    while ((line = br.readLine()) != null) {
      if (count == 0) {
        numberOfLines = Integer.parseInt(line);
      } else {
        lines.add(line);
        if (count == numberOfLines || count > numberOfLines) {
          break;
        }
      }
      count++;
    }
    //    System.out.println(lines);
    compute(lines);
  }
}
