package com.example.helloworld;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random; 

// https://caveofprogramming.teachable.com/courses/2089/lectures/40174
// Multiple Locks; Using Synchronized Code Blocks

public class MultipleLocks {

	private static Random random = new Random();
	
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	private static List<Integer> list1 = new ArrayList<Integer>();
	private static List<Integer> list2 = new ArrayList<Integer>();
	
	private List<Integer> synchronizedList1 = Collections.synchronizedList(list1);
	private List<Integer> synchronizedList2 = Collections.synchronizedList(list2);
	
	public static /*synchronized*/ void stageOne() {
		
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			list1.add(random.nextInt(100));
		}
	}
	
	public static /*synchronized*/ void stageTwo() {

		synchronized (lock2) {		
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			list2.add(random.nextInt(100));
		}
	}
	
	public static void process() {
	
		for(int i=0; i<300; i++) {
			stageOne();
			stageTwo();
		}
	}
	
	public  static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("test");
		
		long start = System.currentTimeMillis();
		//process();
		
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
				
			}
			
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
				
			}
			
		});
		t1.start();
		t2.start();
				
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time take: " + (end-start));
		// 300 x2 x2 ~1.2 seconds using synchronized 
		System.out.println("List1: " + list1.size() +"");
		System.out.println("List2: " + list2.size() +"");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
				
			}
			
		}).start();

	}

}
