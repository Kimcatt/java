package examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @date 2018.7.6
 */
public class ExecutorsExamples {

	static class SimpleTask implements Runnable {
		private int i = 0;
		
		private ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		public SimpleTask(int i) {
			super();
			this.i = i;
		}

		@Override
		public void run() {
			for (int j = 0; j < 100; j++) {
				try {
					Thread.sleep(rand.nextInt(100));
					int x = rand.nextInt(100);
					int y = rand.nextInt(100);
					System.out.format("%-2s + %-2s = %-3s%n", x, y, x + y);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.format("Task #%s done!%n", i);
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		int i = 0;
		for (i = 0; i < 100; i++) {
			executorService.submit(new SimpleTask(i));
		}
		executorService.shutdown();
	}

}
