package example;

import java.util.concurrent.CountDownLatch;

/**
 * @date 2018.7.12
 */
public class CountDownLatchExample {
	
	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(2);
		
		new Thread() {
			@Override
			public void run() {
				try {
					System.out.format("子线程%s正在执行%n", Thread.currentThread().getName());
					Thread.sleep(5000);
					System.out.format("子线程%s执行完毕%n", Thread.currentThread().getName());
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				try {
					System.out.format("子线程%s正在执行%n", Thread.currentThread().getName());
					Thread.sleep(5000);
					System.out.format("子线程%s执行完毕%n", Thread.currentThread().getName());
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		try {
			System.out.println("等待两个子线程执行完毕...");
			latch.await();
			System.out.println("两个子线程执行完毕");
			System.out.println("继续执行主线程");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
