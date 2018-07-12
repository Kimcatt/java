package example;

/**
 * 使用Object.wait()和Object.notify()协作
 * 
 * @date 2018.7.12
 */
public class WaitAndNotifyExample {

	public static Object object = new Object();

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			WaitThread waiter = new WaitThread();
			NotifyThread notifier = new NotifyThread();
			
			waiter.start();
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			notifier.start();
		}
	}

	static class WaitThread extends Thread {
		@Override
		public void run() {
			synchronized (object) {
				try {
					/*
					 * wait
					 * 
					 * The current thread must own this object's monitor. The thread releases
					 * ownership of this monitor and waits until another thread notifies threads
					 * waiting on this object's monitor to wake up either through a call to the
					 * notify method or the notifyAll method. The thread then waits until it can
					 * re-obtain ownership of the monitor and resumes execution.
					 * 
					 * As in the one argument version, interrupts and spurious wakeups are possible,
					 * and this method should always be used in a loop:
					 * 
					 * synchronized (obj) { while (<condition does not hold>) obj.wait(); ... //
					 * Perform action appropriate to condition }
					 * 
					 * This method should only be called by a thread that is the owner of this
					 * object's monitor. See the notify method for a description of the ways in
					 * which a thread can become the owner of a monitor.
					 */
					object.wait();
				} catch (InterruptedException e) {
				}
				System.out.format("线程%s获取到了锁%n", Thread.currentThread().getName());
			}
		}
	}

	static class NotifyThread extends Thread {
		@Override
		public void run() {
			synchronized (object) {
				/*
				 * notify
				 * 
				 * This method should only be called by a thread that is the owner of this
				 * object's monitor. A thread becomes the owner of the object's monitor in one
				 * of three ways: 
				 * •By executing a synchronized instance method of that object.
				 * •By executing the body of a synchronized statement that synchronizes on the
				 * object. 
				 * •For objects of type Class, by executing a synchronized static method
				 * of that class.
				 * 
				 * Only one thread at a time can own an object's monitor.
				 */
				object.notify();
				System.out.format("线程%s调用了object.notify()%n", Thread.currentThread().getName());
			}
			System.out.format("线程%s释放了锁%n", Thread.currentThread().getName());
		}
	}

}
