
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Добавим счетчик, подсчитывающий количество вызовов.
 * Почему счетчик не растет последовательно, а прыгает?
 */
public class SynchronizedTutor2 {
	Integer counter = 0;

	class TestThread implements Runnable {
		String threadName;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			for (int i=0;i<10;i++) {
				counter++;
				System.out.println(threadName+":"+i+":"+counter);
				Thread.yield();
			}
		}
	}
	
	@Test
	public void testThread() {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i=0;i<10;i++) {
			threads.add(new Thread(new TestThread("t"+i)));
		}
	    System.out.println("Starting threads");
		for (int i=0;i<10;i++) {
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
