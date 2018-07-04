package examples;

public class DecrementThread implements Runnable {
	private Counter counter = null;
	
	public DecrementThread(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void run() {
		if (counter != null) {
			for (int i = 0; i < 100000; i++) {
				counter.decrement();
			}
		}
	}
	
}
