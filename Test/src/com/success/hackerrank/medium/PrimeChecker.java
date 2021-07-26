package com.success.hackerrank.medium;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * ou are given a class Solution and its main method in the editor. Your task is to create a class
 * Prime. The class Prime should contain a single method checkPrime.
 *
 * <p>The locked code in the editor will call the checkPrime method with one or more integer
 * arguments. You should write the checkPrime method in such a way that the code prints only the
 * prime numbers.
 *
 * <p>Please read the code given in the editor carefully. Also please do not use method overloading!
 *
 * <p>Note: You may get a compile time error in this problem due to the statement below:
 *
 * <p>BufferedReader br=new BufferedReader(new InputStreamReader(in)); This was added intentionally,
 * and you have to figure out a way to get rid of the error.
 *
 * <p>Input Format
 *
 * <p>There are only five lines of input, each containing one integer.
 *
 * <p>Output Format
 *
 * <p>There will be only four lines of output. Each line contains only prime numbers depending upon
 * the parameters passed to checkPrime in the main method of the class Solution. In case there is no
 * prime number, then a blank line should be printed.
 *
 * <p>Sample Input 
 * 	2 
 * 	1 
 * 	3 
 * 	4 
 * 	5
 *
 * <p>Sample Output:
 *  2 
 *  2 
 *  2 3 
 *  2 3 5
 *
 * @author Tamil
 */
public class PrimeChecker {

  public static void main(String[] args) {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      int n1 = Integer.parseInt(br.readLine());
      int n2 = Integer.parseInt(br.readLine());
      int n3 = Integer.parseInt(br.readLine());
      int n4 = Integer.parseInt(br.readLine());
      int n5 = Integer.parseInt(br.readLine());
      Prime ob = new Prime();
      ob.checkPrime(n1);
      ob.checkPrime(n1, n2);
      ob.checkPrime(n1, n2, n3);
      ob.checkPrime(n1, n2, n3, n4, n5);
      Method[] methods = Prime.class.getDeclaredMethods();
      Set<String> set = new HashSet<>();
      boolean overload = false;
      for (int i = 0; i < methods.length; i++) {
        if (set.contains(methods[i].getName())) {
          overload = true;
          break;
        }
        set.add(methods[i].getName());
      }
      if (overload) {
        throw new Exception("Overloading not allowed");
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}

class Prime {
  void checkPrime(Integer... numbers) {
	  StringBuilder prime = new StringBuilder();
    for (Integer number : numbers) {
      if(isPrime(number)) {
    	  prime.append(number);
    	  prime.append(" ");
      }
    }
    System.out.println(prime.toString());
  }

  boolean isPrime(int number) {
    if (number <= 1) {
      return false;
    }
    if (number == 2) {
      return true;
    }
    for (int i = 2; i <= Math.sqrt(number); i++) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }
}
