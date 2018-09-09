import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;
//import java.util.concurrent.CancellationException;

import org.junit.Test;

/* 
 * Interface Callable <T> allows to create threads that return the execution result T.
 * 1) Try to run the class and look at the results and execution time. Replace the call of newSingleThreadExecutor () to newFixedThreadPool() and compare the execution time.
 * 2) Instead of executorService.execute() use executorService.submit(). Save results in the array of Future objects.
 * 3) Try to stop the execution of the first 5 streams using the method cancel(). Handle CancellationException.
 * 4) Before finishing the thread add sleep for 15 milliseconds. Experiment with the size of the pool: how many threads will complete the work, and how many do not (set, for example, pool of 2).
 * On termination of the thread, add logging, informing if the work was finished:
 * log ("canceling thread" + i + ", isDone =" + results.get(i).isDone());
 * 5) Add a message after the completion of sleep of StringGenerator:
 * log ("thread is finished:" + allStrings [index]);
 * Try to experiment with the parameter of cancel() method: set true or false.
 */

public class CallableTutor {

	public static class StringGenerator implements Callable<String> {

		public String call()  {
        	String[] allStrings = { "Cow", "Goose", "Cat", "Dog", "Elephant", "Rabbit", "Snake", "Chicken", "Horse", "Human" };
			int index = (int)(Math.random()*100)/10;
			System.out.println("thread is started:" + allStrings [index]);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("interrupted "+allStrings [index]);
			}
			//System.out.print (allStrings [index]+" ");
			return allStrings[index];
		}
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		long start = new Date().getTime();

		ArrayList<Future<String>> results = new ArrayList<Future<String>>();

//		ExecutorService executorService = Executors.newSingleThreadExecutor();
		ExecutorService executorService = Executors.newFixedThreadPool(3);
//		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i=0; i<10; i++) {
			results.add(executorService.submit(new StringGenerator()));
		}
		

/*
		// demo of cancellation
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        // try to cancel 5 of 10 threads here
		for (int i=0; i<5; i++) {
			System.out.println("canceling thread " + i + ", " +
					"isDone = " + results.get(i).isDone()+
					(results.get(i).isDone()?" "+results.get(i).get():""));
			results.get(i).cancel(true);
		}
		*/
		int i=0;
		StringBuilder resultStr = new StringBuilder();
		for(Future<String> result: results){
			try {
				//if(!result.isCancelled()) {

					// The blocking get call
					String res = result.get();
					System.out.print(" ["+res + "] ");

					resultStr.append(res);
					resultStr.append(" ");
				//}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (CancellationException e) {
			}
		}
		System.out.println();
		System.out.println("Results: "+resultStr);

		executorService.shutdown();
		try {
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long time = new Date().getTime()-start;
		System.out.println("Time of work: " + time);

	}

}
