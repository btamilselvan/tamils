package com.success.java8;

import java.util.function.Consumer;

public class TestConsumer {
    public static void main(String[] args) {
    	//like a setter method.. accepts input but no return
        Consumer < String > consumer1 = (arg) -> {
            System.out.println(arg + "OK");
        };
        consumer1.accept("TestConsumerAccept - ");
        Consumer < String > consumer2 = (x) -> {
            System.out.println(x + "OK!!!");
        };
        consumer1.andThen(consumer2).accept("TestConsumerAfterThen - ");
    }
}
