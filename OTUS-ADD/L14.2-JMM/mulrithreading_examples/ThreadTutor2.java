
import org.junit.Test;

/**
 * 1) Теперь счет доходит до 100, почему?
 * 2) Установите приоритеты потоков. 
 *     Обратите внимание, как изменился порядок вызовов. 
 *     Обратите внимание на вероятностный характер указаний приоритета.
 *  3) Попробуйте вместо Thread.yield() использовать Thread.sleep() с разными интервалами
 */
public class ThreadTutor2 {

	static class SleepThread implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
				System.out.println("sleep thread finished");

			} catch (InterruptedException e) {
				System.out.println("sleep thread interrupted!");
				e.printStackTrace();
			}

		}
	}

	class TestThread implements Runnable {
		String threadName;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			for (int i=0;i<100;i++) {
				System.out.println(threadName+":"+i);
				Thread.yield();
			}
		}
	}
	
	@Test
	public void testThread() {
		Thread t1 =new Thread(new TestThread("t1"));
		Thread t2 = new Thread(new TestThread("t2"));
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MIN_PRIORITY);
	    System.out.println("Starting threads");
		t1.start();
		t2.start();
		
	    System.out.println("Waiting for threads");
	    try {
			t1.join();
			//t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		Thread t = new Thread(new SleepThread());
		t.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.interrupt();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main finished");
	}
}
