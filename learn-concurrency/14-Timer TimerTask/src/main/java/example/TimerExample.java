package example;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @date 2018.7.12
 */
public class TimerExample {

	public static void main(String[] args) {
		elementaryUsage();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(-1);
	}
	
	public static void elementaryUsage() {
		Timer timer = new Timer();
		int n = 4;
		for (int i = 0; i < n; i++) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.format("正在执行任务，当前线程%s...%n", Thread.currentThread().getName());
				}
			}, 1000);
		}
	}

}
