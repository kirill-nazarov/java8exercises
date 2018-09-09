
import org.junit.Test;

/**
 * Другое решение - это использовать timeout в методе wait(),
 * чтобы поток не подвисал на ожидании, а прерывался в какой-то момент,
 * и проверять счетчик циклов ожидания (или какой-то флаг).
 */
public class WaitTerminateTutor3 {
	Thread t1, t2;
	Object monitor = new Object();
	int runningThreadNumber = 1;

	class TestThread implements Runnable {
		String threadName;
		public boolean shouldTerminate;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			for (int i=0;i<100;i++) {
				System.out.println(threadName+":"+i);
				synchronized(monitor) {
					try {
						int waitingCycle = 0;
						while (!threadName.equals("t"+runningThreadNumber)) {
							System.out.println("wait for thread "+"t"+runningThreadNumber+", waiting cycle:"+waitingCycle);
							monitor.wait(100);
							if (waitingCycle++>=10) {
								System.out.println("too long waiting - we should exit");
								return;
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					runningThreadNumber++;
					if (runningThreadNumber>2) runningThreadNumber=1;
					monitor.notifyAll();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (shouldTerminate) return;
				}
			}
		}
	}
	
	@Test
	public void testThread() {
		TestThread testThread1 = new TestThread("t1");
		t1 = new Thread(testThread1);
		final TestThread testThread2 = new TestThread("t2");
		t2 = new Thread(testThread2);
		System.out.println("Starting threads...");
		t1.start();
		t2.start();

		Thread terminator = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				testThread2.shouldTerminate=true;
			}
		});
		terminator.start();

		System.out.println("Waiting threads to join...");
	    try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
