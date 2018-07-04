package examples;

/**
 * @date 2018.7.4
 */
public class SynchronizedVerification {

	public static void main(String[] args) {
		SynchronizedWrapper wrapper = new SynchronizedWrapper();
		new Thread(new Runnable() {
			@Override
			public void run() {
				wrapper.increment();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				wrapper.decrement();
			}
		}).start();
		
	}

	
	static class SynchronizedWrapper {
		
		private int v = 0;
		
		public synchronized void increment() {
			System.out.println("Begin increment");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			v += 1;
			System.out.println("End increment");
		}
		
		public synchronized void decrement() {
			System.out.println("Begin decrement");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			v -= 1;
			System.out.println("End decrement");
		}
		
		public synchronized int intValue() {
			return v;
		}
		
	}
	
}


