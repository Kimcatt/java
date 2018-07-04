package examples;

class AtomicCounter extends Counter {
	private volatile int c = 0;

	@Override
	public void increment() {
		c++;
	}

	@Override
	public void decrement() {
		c--;
	}

	@Override
	public int value() {
		return c;
	}

}
