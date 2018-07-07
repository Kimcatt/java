package example;

import java.io.IOException;

public class JoinExample {

	public static void main(String[] args) throws IOException {
		System.out.println("进入线程" + Thread.currentThread().getName());
		JoinExample example = new JoinExample();
		SleepThread t1 = example.new SleepThread();
		t1.start();
		try {
			System.out.println("线程" + Thread.currentThread().getName() + "等待");
			t1.join();
			System.out.println("线程" + Thread.currentThread().getName() + "继续执行");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class SleepThread extends Thread {
		@Override
		public void run() {
			System.out.println("进入线程" + Thread.currentThread().getName());
			try {
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
			System.out.println("线程" + Thread.currentThread().getName() + "执行完毕");
		}
	}
}
