import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * In this example we use {@link CountDownLatch} to implement new year counting down.
 *
 * @author BKuczynski
 */
public class CountDownTutor {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(10);
		Thread finalCountDown = new Thread(new FinalCountDown(latch));

		finalCountDown.start();

		Thread europe = new Thread(new Europe(latch));
		europe.start();

	}
}

class FinalCountDown implements Runnable {

	private final CountDownLatch latch;

	FinalCountDown(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("It's final count down");
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Happy new Year!");
	}
}

class Europe implements Runnable {

	private final CountDownLatch latch;

	Europe(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		while (latch.getCount() != 0) {
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("beep!");
				latch.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}