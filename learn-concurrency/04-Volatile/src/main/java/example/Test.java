package example;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Volatile只能保证可见性，不能保证原子性(三大特性：原子性、可见性以及有序性，保证程序的正确运行)
 * 
 * Volatile需要的使用条件：
 * 1) 对变量的写操作不依赖于当前值
 * 2) 该变量没有包含在具有其他变量的不变式中(理解：不变式中，或者只依赖于volatile变量本身，或者不依赖于volatile变量)
 * 
 * Java中使用volatile的几个场景：
 * 1) 状态标记量(线程是否继续运行标记)
 * 2) Double check(单例模式的双重检测)
 * 
 * @date 2018.7.9
 */
public class Test {
	
	public volatile int inc = 0;
	
	public AtomicInteger counter = new AtomicInteger();

	/**
	 * 实现原子操作的方式
	 * 1. Synchronized
	 * 2. Lock
	 * 3. Atomic Package
	 */
	public void increase() {
		inc++; // 非原子操作
		counter.getAndIncrement(); //原子操作
	}

	public static void main(String[] args) {
		final Test test = new Test();
		for (int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					for (int j = 0; j < 1000; j++)
						test.increase();
				};
			}.start();
		}

		while (Thread.activeCount() > 1) // 保证前面的线程都执行完
			Thread.yield();
		System.out.format("%s : %s%n", test.inc, test.counter.get());
	}
}
