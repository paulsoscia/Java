package com.example.helloworld;

import java.util.Scanner;

class Processor extends Thread {

	/* never cache this variable named 'running'  -> by using the keyword 'volatile' */
	private volatile boolean running = true;
	
	
	public void run() {
		
		while (running) {
			System.out.println("From child thread (extends Thread class) Hello");
			
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown() {
		running = false;
	}
}

public class whyVolatile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("main BEGIN");
		Processor proc1 = new Processor();
		proc1.start();
		
		System.out.println("Press return to stop ...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
		proc1.shutdown();
 
		System.out.println("main END");
	}

}
