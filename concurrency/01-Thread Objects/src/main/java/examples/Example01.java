package examples;

/**
 * Defining and Starting a Thread
 * 
 * @date 2018.7.3
 */
public class Example01 {

	public static void main(String[] args) {
		System.out.format("Main %s%n", Thread.currentThread());
		new Thread(new HelloRunnable()).start();
		new HelloThread().start();
	}

}

class HelloRunnable implements Runnable {

	@Override
	public void run() {
		System.out.format("Hello from %s%n", Thread.currentThread());
	}
}

class HelloThread extends Thread {
	@Override
	public void run() {
		System.out.format("Hello from %s%n", Thread.currentThread());
	}
}
