package examples;

class SyncCounter extends Counter {
	private int c = 0;
	
	//private Object lock = new Object();
	
	@Override
	public synchronized void increment() {
			c++;
	}

	@Override
	public synchronized void decrement() {
			c--;
	}

	@Override
	public synchronized int value() {
		return c;
	}

}
