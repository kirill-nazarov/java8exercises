

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * 1) При синхронизации потоки t1 и t2 должны выводить строки попарно:
 * 		11;22;11;22;22;11;
 * 		при этом поток чтения должен выдавать строки, только когда работа завершена,
 * 		т.е. в конце строки должна стоять точка с запятой.
 * 		Что в данном примере не так? Что надо исправить, чтобы строки выводились правильно?
 * 2) Обратите внимание на количество итераций и время выполнения.
 * 		Попробуйте увеличить число итераций в 10 раз.
 * 		Попробуйте заменить обращение lock.lock() на lock.tryLock()
 * 		Почему изменилось время выполнения?
 * 		Также можете добавить tryLock(time, timeUnit) и обратить внимание, как меняется время
 * 		выполнения.
 * 3) Обратите внимание, что операция чтения у нас - медленная (стоит искуственная задержка).
 * 		Это имитирует доступ к медленному ресурсу или долгий процесс.
 * 		Добавьте еще один ReadingThread, назовите его r2 и 
 * 		раскомментируйте строку вывода в лог сообщения, 
 * 		что ReadingThread вышел из сна.
 * 		
 * 		Попробуйте заменить ReentrantLock на ReadWriteLock, 
 * 		передав соответственно lock.writeLock() и lock.readLock().
 *  
 * 		Как изменилось время выполнения? (осталось тем же)
 * 		Как изменился размер выведенных строк?
 * 		(сильно увеличилось, так как в то время, пока поток ReadingThread думал,
 * 		потоки WritingThread продолжали работу)
 * 
 */
public class LockTutor {
	Thread t1, t2, t3;
	Object monitor = new Object();
	int runningThreadNumber = 1;
	StringBuffer stringBuilder = new StringBuffer("");
	public static long ITERATIONS = 200000;

	class WritingThread implements Runnable {
		String threadName;
		Lock lock;

		public WritingThread(String threadName, Lock lock) {
			this.threadName = threadName;
			this.lock = lock;
		}

		@Override
		public void run() {
			for (int i = 0; i < ITERATIONS; i++) {
				lock.lock();
				stringBuilder.append(threadName);
				Thread.yield();
				stringBuilder.append(threadName);
				Thread.yield();
				stringBuilder.append(",");
				lock.unlock();
				Thread.yield();
			}
		}
	}

	class ReadingThread implements Runnable {
		String threadName;
		Lock lock;

		public ReadingThread(String threadName, Lock lock) {
			this.threadName = threadName;
			this.lock = lock;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				String s = stringBuilder.toString();
				int len = s.length();
				int l = len>50?len-50:0;
				System.out.println(len+":"+s.substring(l));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Thread.yield();
			}
		}
	}

	@Test
	public void testThread() {
		long start = new Date().getTime();
		t1 = new Thread(new WritingThread("1", new ReentrantLock()));
		t2 = new Thread(new WritingThread("2", new ReentrantLock()));
		t3 = new Thread(new ReadingThread("2", new ReentrantLock()));
		System.out.println("Starting threads");
		t1.start();
		t2.start();
		t3.start();

		System.out.println("Waiting for threads");
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long time =new Date().getTime()-start;
		System.out.println("Time of work:"+time);
	}

}
