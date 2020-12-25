package com.success.two;

import java.lang.reflect.Method;

import com.success.one.base.SimpleOne;
import com.success.one.base.services.IOne;
import com.success.one.base.services.OneImpl;

public class SimpleTwo {

  public static void main(String[] args) {
    try {
      SimpleOne so = new SimpleOne();
      System.out.println(so.concatPublicString("testing"));
      System.out.println("done");

      Method publicMethod = SimpleOne.class.getDeclaredMethod("concatPublicString", String.class);
      System.out.println(publicMethod.invoke(so, " from simple two"));
      
      Method privateMethod = SimpleOne.class.getDeclaredMethod("concatPrivateString", String.class);
      privateMethod.setAccessible(true);
      System.out.println(privateMethod.invoke(so, " from simple two"));
      
      IOne oneI = new OneImpl();
      System.out.println(oneI.add(20, 11));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
