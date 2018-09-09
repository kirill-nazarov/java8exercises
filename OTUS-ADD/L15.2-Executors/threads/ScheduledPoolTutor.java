
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * in this example we will play with {@link ScheduledThreadPoolExecutor}.
 *
 */
public class ScheduledPoolTutor {

	public static void main(String[] args) {
		ScheduledThreadPoolExecutor scheduled =
				(ScheduledThreadPoolExecutor)
						Executors.newScheduledThreadPool(5);
		AtomicInteger number = new AtomicInteger(1);

		List<SimpleScheduledTask> collect =
				Stream.generate(()->new SimpleScheduledTask(number.getAndIncrement()))
						.limit(10)
						.collect(Collectors.toList());

		executeBy(scheduled, collect, "Scheduled");

	}

	private static void executeBy(ScheduledThreadPoolExecutor executor,
								  List<SimpleScheduledTask> collect,
								  String name) {
		//collect.forEach(t -> executor.schedule(t, t.getNumber(), TimeUnit.SECONDS));
		//collect.forEach(t -> executor.scheduleAtFixedRate(t, 0, 3, TimeUnit.SECONDS));

		try {
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdownNow();
		printInfo(executor, name);
	}

	public static void printInfo(ThreadPoolExecutor executor, String name) {
		System.out.printf("%n");
		System.out.printf("***************************%n");
		System.out.printf("%s executor executing %s tasks%n", name, executor.getTaskCount());
		System.out.printf("%s executor maximum pool size is %s%n", name, executor.getMaximumPoolSize());
		System.out.printf("%s executor largest pool size was %s%n", name, executor.getLargestPoolSize());
		System.out.printf("%s executor core pool size was %s%n", name, executor.getCorePoolSize());
		System.out.printf("***************************%n");
	}

}

class SimpleScheduledTask implements Runnable {
	private int number;
	SimpleScheduledTask(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public void run() {
		System.out.println("start "+number);
		try {
			Thread.sleep(1000);
			System.out.println("finished "+number);
		} catch (InterruptedException e) {
			System.out.println("interrupted "+number);
		}
	}
}