package example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 回环栅栏，CyclicBarrier
 * 
 * @date 2018.7.12
 */
public class CyclicBarrierExample {

	public static void main(String[] args) {
		// elementaryUsage();
		// callbackUsage();
//		timedAwaitUsage();
		reusingUsage();
	}

	/**
	 * 基本用法
	 */
	private static void elementaryUsage() {
		// 基本用法
		int n = 4;
		CyclicBarrier barrier = new CyclicBarrier(n);
		for (int i = 0; i < 4; i++) {
			new Writer(barrier).start();
		}
	}

	/**
	 * 回调用法
	 */
	private static void callbackUsage() {
		// 回调用法
		int n = 4;
		CyclicBarrier barrier = new CyclicBarrier(n, new Runnable() {
			@Override
			public void run() {
				System.out.format("线程%s执行回调任务...%n", Thread.currentThread().getName());
			}
		});
		for (int i = 0; i < 4; i++) {
			new Writer(barrier).start();
		}
	}

	/**
	 * 带有等待时间的await示例
	 */
	private static void timedAwaitUsage() {
		int n = 4;
		CyclicBarrier barrier = new CyclicBarrier(n);
		for (int i = 0; i < n - 1; i++) {
			new Writer(barrier).start();
		}
		try {
			Thread.sleep(5000);
			new Writer(barrier).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重用CyclicBarrier
	 */
	private static void reusingUsage() {
		int n = 4;
		CyclicBarrier barrier = new CyclicBarrier(n);

		for (int i = 0; i < n; i++) {
			new Writer(barrier).start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("CyclicBarrier重用");

		for (int i = 0; i < n; i++) {
			new Writer(barrier).start();
		}
	}

	static class Writer extends Thread {

		private CyclicBarrier barrier;

		public Writer(CyclicBarrier barrier) {
			this.barrier = barrier;
		}

		@Override
		public void run() {
			System.out.format("线程%s正在写入数据...%n", Thread.currentThread().getName());
			try {
				Thread.sleep(5000); // 以睡眠来模拟写入数据操作
				System.out.format("线程%s写入数据完毕，等待其他线程写入完毕%n", Thread.currentThread().getName());
//				barrier.await(2000, TimeUnit.MILLISECONDS);
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.format("线程%s结束等待，继续处理其他任务...%n", Thread.currentThread().getName());
		}
	}

}
