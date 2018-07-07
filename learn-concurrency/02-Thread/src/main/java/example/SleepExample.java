package example;

import java.io.IOException;

/**
 * Sleep方法不会释放锁
 * 
 * @date 2018.7.7
 */
public class SleepExample {

	private int i = 10;
	private Object object = new Object();

	public static void main(String[] args) throws IOException {
		SleepExample test = new SleepExample();
		SleepThread thread1 = test.new SleepThread();
		SleepThread thread2 = test.new SleepThread();
		thread1.start();
		thread2.start();
	}

	class SleepThread extends Thread {
		
		@Override
		public void run() {
			synchronized (object) {
				i++;
				System.out.println("i:" + i);
				try {
					System.out.println("线程" + Thread.currentThread().getName() + "进入睡眠状态");
					Thread.currentThread().sleep(10000);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
				System.out.println("线程" + Thread.currentThread().getName() + "睡眠结束");
				i++;
				System.out.println("i:" + i);
			}
		}
	}
}
