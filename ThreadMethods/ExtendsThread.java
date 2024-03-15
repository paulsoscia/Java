import java.io.*;
import java.lang.Thread;
import java.util.Random;

// MultiThreading Method #1 Extend the Thread Class 
//                       #2 Implement Runnable and pass it constructor of Thread Class  
class ExtendsThread extends Thread {

    public Integer ClassID;
    public Integer ClassID2;
      
    public void run() 
    { 
    
        ClassID = new Random().nextInt(1000);
        Integer LocalID = new Random().nextInt(1000);   
        System.out.println("Run method executed by child Thread (begin) ClassID=" + ClassID + ";LocalID="+LocalID);
         
        try {
            //sleep is static method of Thread class 
            Thread.sleep(ClassID);
        } catch (InterruptedException e){
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("Run method executed by child Thread   (end) ClassID=" + ClassID + ";LocalID="+LocalID); 
    } 

    public static void main(String[] args) 
    { 
        ExtendsThread t1 = new ExtendsThread();
        ExtendsThread t2 = new ExtendsThread();
        
        // Main thread instead of the child thread
        //t1.run(); 
        // Main thread instead of the child thread
        
        // Child Thread 
        t1.start(); 
        // Child Thread 
                
        // Main thread
        try {
                Thread.sleep(100);
                System.out.println("Main method executed by main thread");
        }
        catch (Exception e) {     
            // catching the exception
            System.out.println(e);
        }
        // Main thread 
        
        // Child Thread 
        t2.start();
        // Child Thread   
    } 
}
