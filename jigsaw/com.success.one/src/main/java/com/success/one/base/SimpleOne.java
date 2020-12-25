package com.success.one.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class SimpleOne {

  private String simplePrivateString = "hello private";
  public String simplePublicString = "hello public";

  public String getSimplePrivateString() {
    return simplePrivateString;
  }

  public void setSimplePrivateString(String simplePrivateString) {
    this.simplePrivateString = simplePrivateString;
  }

  public String getSimplePublicString() {
    return simplePublicString;
  }

  public void setSimplePublicString(String simplePublicString) {
    this.simplePublicString = simplePublicString;
  }

  private String concatPrivateString(String str) {
    return this.simplePrivateString.concat(" ").concat(str);
  }

  public String concatPublicString(String str) {
    return this.simplePublicString.concat(" ").concat(str);
  }

  public static void main(String[] args) {
    try {
      Stream.of(SimpleOne.class.getFields()).forEach(field -> System.out.println(field.getName()));

      System.out.println("-------------------------------------------------");

      Stream.of(SimpleOne.class.getDeclaredFields())
          .forEach(field -> System.out.println(field.getName()));

      System.out.println("-------------------------------------------------");
      System.out.println("-------------------------------------------------");

      Stream.of(SimpleOne.class.getMethods()).forEach(field -> System.out.println(field.getName()));
      System.out.println("-------------------------------------------------");
      Stream.of(SimpleOne.class.getDeclaredMethods())
          .forEach(field -> System.out.println(field.getName()));

      SimpleOne so = new SimpleOne();
      Method privateMethod = SimpleOne.class.getDeclaredMethod("concatPrivateString", String.class);
      String result = (String) privateMethod.invoke(so, "private world");
      System.out.println("result from private method: " + result);

      System.out.println("-------------------------------------------------");

      Field privateField = SimpleOne.class.getDeclaredField("simplePrivateString");
      String pfValue = (String) privateField.get(so);
      System.out.println("private field value: " + pfValue);
      privateField.set(so, "new modified private value");
      pfValue = (String) privateField.get(so);
      System.out.println("modified private field value: " + pfValue);
      
      System.out.println("-------------------------------------------------");
      
      Method publicSetter = SimpleOne.class.getDeclaredMethod("setSimplePrivateString", String.class);
      publicSetter.invoke(so, "set thru public setter");
      System.out.println(so.getSimplePrivateString());
      

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
