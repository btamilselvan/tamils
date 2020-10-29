package com.success.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class TestCompletableFuture {

  public static void main(String[] args) throws InterruptedException {
    /*
     * CompletableFuture<Supplier<String>> cf = new
     * CompletableFuture<Supplier<String>>(); cf.thenAccept(data -> data.get());
     * System.out.println("Built..."); cf.complete(() -> sayHello());
     * System.out.println("Done");
     */
    CompletableFuture<String> cs = CompletableFuture.supplyAsync(() -> sayHello());

    CompletableFuture.runAsync(() -> sayHello());

    System.out.println("Done 1");
    System.out.println("Should be main " + Thread.currentThread().getName());
    try {
      cs.get();
    } catch (InterruptedException | ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //		cf1.thenAccept(data-> System.out.println(data));
  }

  public static String sayHello() {
    try {
      System.out.println("Should be FJP " + Thread.currentThread().getName());
      Thread.sleep(5000);
      System.out.println("Saying hello");
      System.out.println("Should be FJP" + Thread.currentThread().getName());
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "Hello!!!";
  }
}
