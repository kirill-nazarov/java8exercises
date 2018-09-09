
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 1) Создадим сразу 100 потоков, в каждом из которых будут выводиться числа от 0 до 9.
 * 2) Добавьте поле counter, и считайте количество вызовов, добавив в метод run()
 * 		counter++
 *  	Почему значения счетчика не растут последовательно, а прыгают?
 *  3) Заключим содержимое метода run() в блок synchronized(counter) { ... }
 *  	 Удалось ли нам заставить счетчик расти последовательно? Почему?
 *  4) Добавьте отдельное поле как объект-монитор синхронизации.
 *  		Object lock = new Object();
 *  	И используйте этот объект для блока синхронизации: synchronized(lock) {...} 
 *  	 Удалось ли нам заставить счетчик расти последовательно? Почему?
 *
 */
public class SynchronizedTutor {
	class TestThread implements Runnable {
		String threadName;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			for (int i=0;i<10;i++) {
				System.out.println(threadName+":"+i);
			}
		}
	}
	
	@Test
	public void testThread() {
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
	}

}
