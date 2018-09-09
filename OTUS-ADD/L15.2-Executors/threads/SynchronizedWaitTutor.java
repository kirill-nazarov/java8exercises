
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 		Теперь задача состоит в том, чтобы выполнять по очереди все потоки, т.е. упорядочить
 * 		их выполнение, чтобы вызывались потоки t1, t2, t3, t4, t5 и т.д. 
 * 		Как это сделать?
 * 
 * 		Почему в приведенном решении потоки все-таки не упорядочены?
 */
public class SynchronizedWaitTutor {
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
					System.out.println(threadName+":"+i+":"+runningThread);
					runningThread++;
					if (runningThread>=100) runningThread = 0;
					Thread.yield();
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
