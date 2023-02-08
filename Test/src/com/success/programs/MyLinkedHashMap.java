package com.success.programs;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MyLinkedHashMap<K, V> {

  int bucketSize = 16;
  Bucket<K, V>[] myBucket = new Bucket[bucketSize];

  public void put(K key, V value) {
    int bucketId = getBucketId(key);
    Bucket<K, V> currBucket = myBucket[bucketId];
  }

  public int getBucketId(K key) {
    if (key == null) {
      return 1;
    }
    return Math.abs(key.hashCode() % bucketSize);
  }

  public static void main(String[] args) {
    Map<String, String> map = new LinkedHashMap<>();
    map.put(null, "121");

    Map<String, String> table = new Hashtable<>();
    //    table.put(null, "121");

    System.out.println(Math.abs(Integer.MIN_VALUE));
    Person p = new Person();

    //    p.hashCode() %

    if (p.hashCode() != 0) {
      Math.abs(p.hashCode());
    }

    if (Math.abs(p.hashCode()) > 0) {
      System.out.println("sss");
    } else {
      System.out.println("ddd");
    }
  }
}

class Person {
  String name;

  public int hashCode() {
    return Integer.MIN_VALUE;
  }
}

class Bucket<K, V> {
  Bucket<K, V> previous;
  Bucket<K, V> next;
  MyEntry<K, V> entry;
}

class MyEntry<K, V> {
  K key;
  V value;
  MyEntry<K, V> next;
}
