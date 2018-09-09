
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class SynchronizedTutor4 {
	Integer counter = 0;
	Object lock = new Object();

	class TestThread implements Runnable {
		String threadName;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			for (int i=0;i<10;i++) {
				synchronized(lock) {
					counter++;
					System.out.println(threadName+":"+i+":"+counter);
					Thread.yield();
				}
			}
		}
	}
	
	@Test
	public void testThread() {
		long start = new Date().getTime();
		List<Thread> threads = new ArrayList<Thread>();
		for (int i=0;i<100;i++) {
			threads.add(new Thread(new TestThread("t"+i)));
		}
	    System.out.println("Starting threads");
		for (int i=0;i<100;i++) {
			threads.get(i).start();
		}
	    System.out.println("Waiting for threads");
	    try {
			for (int i=0;i<100;i++) {
				threads.get(i).join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long time =new Date().getTime()-start;
		System.out.println("Time of work:"+time);
	}

}
