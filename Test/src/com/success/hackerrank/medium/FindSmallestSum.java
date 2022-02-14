package com.success.hackerrank.medium;

import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
/**
 * Find the  smallest sum of the array after performing "N" number of division operations in its elements.
 * @author btamilselvan
 *
 */
public class FindSmallestSum {
  private static int smallestSum(List<Integer> nums, int numOfOperations) {

    SortedMap<Integer, Long> numCountMap = new TreeMap<>();
    // group the nums by count
    numCountMap.putAll(
        nums.stream()
            .collect(
                Collectors.groupingBy(
                    num -> num, Collectors.mapping(num -> num, Collectors.counting()))));

    while (numOfOperations > 0) {
    //easiest way to reduce the sum is to work on the large numbers first.
      Integer largestNum = numCountMap.lastKey();
      long frequency = numCountMap.get(largestNum);
      int dividedValue = (int) Math.ceil((double) largestNum / 2);

      if (numOfOperations >= frequency) {
    	  //the large number count is less than the number of remaining div operations..
        numOfOperations = numOfOperations - (int) frequency;
        // remove the large number
        numCountMap.remove(largestNum);
        // add the divided number.. if the divided number already exists, then increment the counter
        // by 'frequency' times
        numCountMap.put(
            dividedValue,
            numCountMap.get(dividedValue) != null
                ? numCountMap.get(dividedValue) + frequency
                : frequency);
      } else {
//    	  the large number count is greater than the number of remaining div operations.. just work only on this large number
        // reduce the frequency of the large number count by 'numOfOperations'. do division just in
        // this large number
        numCountMap.put(largestNum, frequency - numOfOperations);
        // add the divided number by 'numOfOperations' times
        numCountMap.put(
            dividedValue,
            numCountMap.get(dividedValue) != null
                ? numCountMap.get(dividedValue) + numOfOperations
                : numOfOperations);
        numOfOperations = 0; // no need to look for other numbers...
      }
    }
    return numCountMap
        .entrySet()
        .stream()
        .map(e -> e.getKey() * e.getValue().intValue())
        .reduce(0, Integer::sum);
  }

  public static void main(String[] args) {
    System.out.println(smallestSum(Arrays.asList(200, 100, 150, 170, 7, 3, 7), 5));
  }
}
