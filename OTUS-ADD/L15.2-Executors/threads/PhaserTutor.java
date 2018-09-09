
import java.util.concurrent.Phaser;


public class PhaserTutor {

	public static void main(String[] args) {
		Phaser phaser = new Phaser();
		Thread twoStep = new Thread(new TwoStepTask(phaser));
		Thread threeStep = new Thread(new ThreeStepTask(phaser));

		twoStep.start();
		threeStep.start();

		try {
			twoStep.join();
			threeStep.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.printf("Phaser status %s", phaser.isTerminated());
	}

}


class ThreeStepTask implements Runnable {

	private final Phaser phaser;

	ThreeStepTask(Phaser phaser) {
		this.phaser = phaser;
		this.phaser.register();
	}

	@Override
	public void run() {
		System.out.printf("%s waiting for start%n", Thread.currentThread().getName());
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s is in phase one%n", Thread.currentThread().getName());
		RandomSleeper.rsleep(10);
		System.out.printf("%s finished phase one%n", Thread.currentThread().getName());
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s is in phase two%n", Thread.currentThread().getName());
		RandomSleeper.rsleep(10);
		System.out.printf("%s finished phase two%n", Thread.currentThread().getName());
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s is in phase three%n", Thread.currentThread().getName());
		RandomSleeper.rsleep(10);
		System.out.printf("%s finished phase three%n", Thread.currentThread().getName());
		phaser.arriveAndDeregister();
	}
}

class TwoStepTask implements Runnable {

	private final Phaser phaser;

	TwoStepTask(Phaser phaser) {
		this.phaser = phaser;
		this.phaser.register();
	}

	@Override
	public void run() {
		System.out.printf("%s waiting for start%n", Thread.currentThread().getName());
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s is in phase one%n", Thread.currentThread().getName());
		RandomSleeper.rsleep(10);
		System.out.printf("%s finished phase one%n", Thread.currentThread().getName());
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s is in phase two%n", Thread.currentThread().getName());
		RandomSleeper.rsleep(10);
		System.out.printf("%s finished phase two%n", Thread.currentThread().getName());
		phaser.arriveAndDeregister();
	}
}