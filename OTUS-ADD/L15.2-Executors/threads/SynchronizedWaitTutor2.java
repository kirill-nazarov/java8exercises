
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Почему в приведенном решении 1 потоки не упорядочены?
 * Потому что они не сразу переходят в режим ожидания, а сначала выполняются.
 *
 * И наконец решение, которое позволяет упорядочить все потоки. 
 * Почему мы используем notifyAll()?
 */
public class SynchronizedWaitTutor2 {
	Integer counter = 0;
	Object monitor = new Object();
	List<Thread> threads;
	Integer runningThread = 0;
	
	class TestThread implements Runnable {
		String threadName;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			for (int i=0;i<10;i++) {
				synchronized(monitor) {
					try {
						while (!threadName.equals("t"+runningThread)) {
							monitor.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(threadName+":"+i+":"+runningThread);
					runningThread++;
					if (runningThread>=100) runningThread = 0;
					monitor.notifyAll();
				}
			}
		}
	}
	
	@Test
	public void testThread() {
		threads = new ArrayList<Thread>();
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
	}

}
