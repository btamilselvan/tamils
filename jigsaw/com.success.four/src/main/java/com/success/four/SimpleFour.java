package com.success.four;

import java.util.ServiceLoader;

import com.success.one.base.services.IOne;

public class SimpleFour {
  public static void main(String[] args) {
    // all implementations of IOne will be loaded...
    ServiceLoader.load(IOne.class).forEach(one -> System.out.println(one.add(10, 10)));
  }
}
