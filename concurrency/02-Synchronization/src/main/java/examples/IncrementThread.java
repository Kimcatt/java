package examples;

public class IncrementThread implements Runnable {
	private Counter counter = null;
	
	public IncrementThread(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void run() {
		if (counter != null) {
			for (int i = 0; i < 100000; i++) {
				counter.increment();
			}
		}
	}
	
}
