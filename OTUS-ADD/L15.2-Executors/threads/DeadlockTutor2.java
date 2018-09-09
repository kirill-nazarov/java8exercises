
import org.junit.Test;

public class DeadlockTutor2 {
	Thread t1, t2;
	Account account1 = new Account(1,100);
	Account account2 = new Account(2,50);

	class Account {
		double balance;
		int id;

		public Account(int id, double balance) {
			this.id = id;
			this.balance = balance;
		}

		void withdraw(double amount){
			balance -= amount;
		}

		void deposit(double amount){
			balance += amount;
		}

		void transfer(Account from, double amount) {
			if (from.id > id) {
				// блокируем текущий счет
				synchronized (this) {
					// блокируем счет, с которого производится перевод
					synchronized (from) {
						deposit(amount);
						from.withdraw(amount);
					}
				}
			} else {
				// блокируем текущий счет
				synchronized (from) {
					// блокируем счет, с которого производится перевод
					synchronized (this) {
						deposit(amount);
						from.withdraw(amount);
					}
				}
			}
		}
	}

	@Test
	public void testDeadlock() {
		t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i=0;i<100;i++) {
					account1.transfer(account2, 30);
					System.out.println("acc1->acc2: acc1="+account1.balance+", acc2="+account2.balance);
					Thread.yield();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i=0;i<100;i++) {
					account2.transfer(account1, 30);
					System.out.println("acc2->acc1: acc1="+account1.balance+", acc2="+account2.balance);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		System.out.println("Starting threads");
		t1.start();
		t2.start();

		System.out.println("Waiting for threads");
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
