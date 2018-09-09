
import org.junit.Test;

/**
 * Одно решение - использовать прерывание потока.
 * Можно отмерить потоку какое-то время работы, и это время убивать поток 
 * (также можно использовать другой 	индикатор прекращения ожидания - например, 
 * при нажатии кнопки Прервать в GUI).
 * 
 * Одако решение с terminator здесь не подойдет: ведь поток завис в методе wait(),
 * и никакой флаг остановки он уже не проверяет. Вывести его из этого состояния
 * может только Thread.interrupt().
 */
public class WaitTerminateTutor2 {
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
						while (!threadName.equals("t"+runningThreadNumber)) {
							System.out.println("wait for thread "+"t"+runningThreadNumber);
							monitor.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
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
	    System.out.println("Starting threads");
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
		
		/**
		 * Через 10 секунд будет запущен interruptor, который прервет поток t1.
		 */
		Thread interruptor = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
					t1.interrupt();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		interruptor.start();
		
		System.out.println("Waiting for threads");
	    try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
