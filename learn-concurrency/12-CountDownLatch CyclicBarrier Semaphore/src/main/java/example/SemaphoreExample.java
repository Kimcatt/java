package example;

import java.util.concurrent.Semaphore;

/**
 * 信号量，Semaphore
 * 
 * @date 2018.7.12
 */
public class SemaphoreExample {

	public static void main(String[] args) {
		int n = 8; // 工人数
		Semaphore semaphore = new Semaphore(5); // 机器数目
		for (int i = 0; i < n; i++)
			new Worker(i, semaphore).start();
	}

	static class Worker extends Thread {
		private int num;
		private Semaphore semaphore;

		public Worker(int num, Semaphore semaphore) {
			this.num = num;
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.format("工人%s占用一个机器在生产...%n", this.num);
				Thread.sleep(2000);
				System.out.format("工人%s释放机器%n", this.num);
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
