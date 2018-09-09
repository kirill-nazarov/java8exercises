
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * In this example we will play with {@link ExecutorService#invokeAny(Collection)} method.
 *
 */
public class InvokeAnyTutor {

	public static void main(String[] args) {
		ExecutorService clientAdapter = Executors.newCachedThreadPool();
		Collection<FxAgencyRequest> requests = new ArrayList<>();
		requests.add(new FxAgencyRequest(new Boomberg()));
		requests.add(new FxAgencyRequest(new Frayers()));

		try {
			Long result = clientAdapter.invokeAny(requests);
			System.out.printf("Current value is %d%n", result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		clientAdapter.shutdown();
	}

}

class FxAgencyRequest implements Callable<Long>{

	private final FxAgency fxAgency;

	FxAgencyRequest(FxAgency fxAgency) {
		this.fxAgency = fxAgency;
	}

	@Override
	public Long call() throws Exception {
		Thread.currentThread().setName(fxAgency.toString());
		return fxAgency.currentValue(Currency.getInstance("USD"));
	}
}


interface FxAgency{

	long currentValue(Currency currency);
}

class Boomberg implements FxAgency{

	@Override
	public long currentValue(Currency currency) {
		System.out.println("Current waiting time is 8 seconds.");
		RandomSleeper.sleep(8);
		return 10L;
	}

	@Override
	public String toString() {
		return "Boomberg";
	}
}

class Frayers implements FxAgency{

	@Override
	public long currentValue(Currency currency) {
		System.out.println("Current waiting time is 5 seconds.");
		RandomSleeper.sleep(5);
		return 8L;
	}


	@Override
	public String toString() {
		return "Frayers";
	}
}