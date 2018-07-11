package example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @date 2018.7.11
 */
public class ThreadPoolExecutorExample {

	public static void main(String[] args) {
		final int queueSize = 3;
		final int taskCount = 13;
		ThreadPoolExecutor executor = new ThreadPoolExecutor(queueSize, 10, 200, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
		try {
			for (int i = 0; i < taskCount; i++) {
				MockTask task = new MockTask(i);
				executor.execute(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
			executor.shutdown();
		}
		executor.shutdown();
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(300);
				System.out.format("线程池中线程数目：%2s, 队列中等待执行的任务数目：%2s, 已执行完的任务数目：%2s%n", executor.getPoolSize(),
						executor.getQueue().size(), executor.getCompletedTaskCount());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class MockTask implements Runnable {

	private int taskNum;

	public MockTask(int num) {
		this.taskNum = num;
	}

	@Override
	public void run() {
		System.out.format("正在执行TASK %2s%n", taskNum);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.format("TASK %2s执行完毕%n", taskNum);
	}

}