package example;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @date 2018.7.7
 */
public class ReentrantLockExample {

	private ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		final ReentrantLockExample test = new ReentrantLockExample();

		Thread t1 = new Thread() {
			public void run() {
				try {
					test.insertUsingLockInterruptibly(Thread.currentThread());
				} catch (InterruptedException e) {
					System.out.format("%s 被中断%n", Thread.currentThread().getName());
				}
			};
		};
		t1.start();

		Thread t2 = new Thread() {
			public void run() {
				try {
					test.insertUsingLockInterruptibly(Thread.currentThread());
				} catch (InterruptedException e) {
					System.out.format("%s 被中断%n", Thread.currentThread().getName());
				}
			};
		};
		t2.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.interrupt();
		
	}

	public void insert(Thread thread) {
		lock.lock();
		try {
			System.out.println(thread.getName() + "得到了锁");
			for (int i = 0; i < 5; i++) {
				arrayList.add(i);
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(thread.getName() + "释放了锁");
			lock.unlock();
		}
	}

	public void insertUsingTryLock(Thread thread) {
		if (lock.tryLock()) {
			try {
				System.out.println(thread.getName() + "得到了锁");
				for (int i = 0; i < 5; i++) {
					arrayList.add(i);
					Thread.sleep(500);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println(thread.getName() + "释放了锁");
				lock.unlock();
			}
		} else {
			System.out.format("%s 获取锁失败%n", thread.getName());
		}
	}

	public void insertUsingLockInterruptibly(Thread thread) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			System.out.println(thread.getName() + "得到了锁");
			for (int i = 0; i < 5; i++) {
				arrayList.add(i);
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
			System.out.println(thread.getName() + "释放了锁");
		}

	}
}
