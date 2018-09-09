
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

public class SemaphoreTutor {

	public static void main(String[] args) {
		Track track = new Track();
		track.rideOn();
	}

}

class Track {
	private final Semaphore semaphore;

	Track() {
		semaphore = new Semaphore(2);
	}

	public void rideOn() {
		Stream.generate(() -> new Train(semaphore))
				.limit(4)
				.map(Thread::new)
				.forEach(Thread::start);
	}
}

class Train implements Runnable {

	private final Semaphore semaphore;

	Train(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		arrival();
		try {
			semaphore.acquire(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		departure();
		RandomSleeper.rsleep(10);
		back();
		semaphore.release(1);

	}

	private void departure() {
		System.out.printf("%s departure at  %tB %<te,  %<tY  %<tT %<Tp%n", Thread.currentThread().getName(), LocalDateTime.now());
	}

	private void arrival() {
		System.out.printf("%s arrived at  %tB %<te,  %<tY  %<tT %<Tp%n", Thread.currentThread().getName(), LocalDateTime.now());
	}

	private void back() {
		System.out.printf("%s backed at  %tB %<te,  %<tY  %<tT %<Tp%n", Thread.currentThread().getName(), LocalDateTime.now());
	}
}