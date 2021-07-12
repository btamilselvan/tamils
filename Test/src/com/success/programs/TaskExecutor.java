package com.success.programs;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TaskExecutor {

	private static void execute() {
		ExecutorService es = Executors.newFixedThreadPool(2);
		System.out.println("1 before run"); //1
		Future<?> f = es.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("before sleep"); //3
				try {
					Thread.sleep(5000);
					System.out.println("after sleep"); //5
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("after run"); //2
		
		try {
			Object obj = f.get(6, TimeUnit.SECONDS);
			if(obj == null) {
				System.out.println("success");
			}else {
				System.out.println("failed");
			}
			System.out.println("return "+obj);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
		es.shutdown();
		System.out.println("after shutdown"); //4
	}
	
	public static void main(String[] args) {
		execute();
	}
}
