package com.example.helloworld;

import java.text.SimpleDateFormat;

public class threadSynchronization {

	private int count = 0 ;

  /* Synchronized keyword is used to prevent 'a threading behavior that depends on timing and interleaving of multiple threads/processes' */
  /*      which can cause two or more threads attempt to update mutable (in this case variable 'count' where it's value can change) shared data at the same time */
  /*      Java uses synchronized to create intrinsic lock (aka mutex/monitor lock ) */
  /*      As long as a thread owns an intrinsic lock, 
  /*         no other thread can acquire the same lock. The other thread will block when it attempts to acquire the lock. */
  /* All threads for the class threadSynchronization share this same/single instrinsic lock */
	public synchronized void increment() {
		/* 10 mill seconds not synchronized per count -> 220000
		   25 mill seconds synchronized     per count -> 220000
		 */
		count++;
	}

	public static void main(String[] args) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new java.util.Date());
		
		threadSynchronization app = new threadSynchronization(); 
		app.doWork();

		String timeStamp2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new java.util.Date());

		System.out.println(timeStamp);
		System.out.println(timeStamp2);
	}

	public void doWork() {

		System.out.println("count="+count);
		
		Thread t1 = new Thread(new Runnable() {
	        
            @Override
            public void run() {       
                for(int i=0; i<110000; i++) {
                	increment();
                }
            }
        });

		Thread t2 = new Thread(new Runnable() {
	        
            @Override
            public void run() {       
                for(int i=0; i<110000; i++) {
                	increment();
                }
            }
        });
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("count="+count);
	}
}
