package com.success.java8;

import java.util.function.Supplier;

public class TestSupplier {

	public static void main(String[] args) {
//		like a getter method, accepts no input but returns input
		//functional interface with just one method T Get -> which returns the type T 
		Supplier<String> supplier = () -> "hello";
		
		String some = supplier.get();
		int num = 10;
		
		Supplier<Integer> sup1 = () -> someMethod(num);
		System.out.println("afte sup1");
		sup1.get();
		
		sup1.get();
	}
	
	private static Integer someMethod(int x) {
		System.out.println("x is "+x);
		return x*10;
	}
}
