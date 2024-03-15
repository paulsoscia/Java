import java.io.*;
import java.lang.Thread;
import java.util.Random;

public class AnonymousClass  {

    public static void main(String[] args) 
    { 
        Thread t1 = new Thread(new Runnable() {
        
            @Override
            public void run() {       
                Integer LocalID = new Random().nextInt(1000);   
                System.out.println("Run method executed by child Thread (begin) ClassID=;LocalID=" + LocalID);
                 
                try {
                    //sleep is static method of Thread class 
                    Thread.sleep(LocalID);
                } catch (InterruptedException e){
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                System.out.println("Run method executed by child Thread   (end) ClassID=;LocalID=" + LocalID);
            }
        });
        
        t1.start();    
    } 
}
