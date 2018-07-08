package example;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @date 2018.7.7
 */
public class ReentrantReadWriteLockExample {

	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	public static void main(String[] args) {
		final ReentrantReadWriteLockExample example = new ReentrantReadWriteLockExample();

		new Thread() {
			public void run() {
				example.getUsingReadLock(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				example.getUsingReadLock(Thread.currentThread());
			};
		}.start();
	}

	public synchronized void get(Thread thread) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start <= 1) {
			System.out.println(thread.getName() + "正在进行读操作");
		}
		System.out.println(thread.getName() + "读操作完毕");
	}

	public void getUsingReadLock(Thread thread) {
		rwLock.readLock().lock();
		try {
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start <= 2) {
				System.out.println(thread.getName() + "正在进行读操作");
			}
			System.out.println(thread.getName() + "读操作完毕");
		} finally {
			rwLock.readLock().unlock();
		}
	}
}
