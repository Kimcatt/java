package examples;

import java.util.Calendar;

/**
 * Join
 * 
 * @date 2018.7.3
 */
public class Example04 {

	public static void main(String[] args) {
		Thread t = new Thread(new MockJob());
		System.out.format("Daemon thread? %s%n", t.isDaemon() ? "yes" : "no");
		t.setDaemon(true);
		t.start();
//		try {
//			t.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.format("Done ! %s%n", Calendar.getInstance().getTime());
	}

}

class MockJob implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
}
