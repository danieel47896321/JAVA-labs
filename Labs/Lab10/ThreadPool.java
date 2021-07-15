package lab10;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	static int NUMBER_OF_TASKS = 6;
	static int COUNTER = 7;
	public static void main(String[] args) {
		Executor exe = Executors.newFixedThreadPool (4);
		for(int i=0 ; i < NUMBER_OF_TASKS; i++) 
			exe.execute(new CountDown(i,COUNTER)); 
		((ExecutorService)exe).shutdown(); 
		 while(!((ExecutorService) exe).isTerminated()){
			 try { Thread.sleep(200); }
			 catch (InterruptedException e) { e.printStackTrace(); }
		 }
		System.out.println("End of work");
	}
}
