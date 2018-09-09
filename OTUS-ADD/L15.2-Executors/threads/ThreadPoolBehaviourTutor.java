
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In this example we compare behaviour of different configurations of {@link ThreadPoolExecutor}.
 * <p>
 * <ul>
 * <li>We create some number of long-running tasks.</li>
 * <li>Execute them via different executors.</li>
 * <li>Compare executors statistics</li>
 * </ul>
 *
 */
public class ThreadPoolBehaviourTutor {

	public static void main(String[] args) {
		ThreadPoolExecutor cached =
				(ThreadPoolExecutor) Executors.newCachedThreadPool();
		ThreadPoolExecutor fixed =
				(ThreadPoolExecutor) Executors.newFixedThreadPool(4);
		ThreadPoolExecutor single =
				(ThreadPoolExecutor) Executors.newFixedThreadPool(1);

		List<LongRunningTask> collect =
				Stream.generate(LongRunningTask::new)
						.limit(40)
						.collect(Collectors.toList());

		executeBy(cached, collect, "Cached");
		executeBy(fixed, collect, "Fixed");
		executeBy(single, collect, "Single");

	}

	private static void executeBy(ThreadPoolExecutor executor, List<LongRunningTask> collect, String name) {
		collect.forEach(executor::execute);
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
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


/**
 * Example log-running task. Will sleep random period of time up to 5s. Then print {@code .} char.
 */
class LongRunningTask implements Runnable {
	@Override
	public void run() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf(".");
	}
}