package com.success.three.services;

import com.success.one.base.services.IOne;

public class ServiceImplThree implements IOne {

  @Override
  public int add(int a, int b) {
    return 2 * (a + b);
  }
}
