package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @date 2018.7.10
 */
public class ConcurrentModificationExceptionDemo {

	public static void main(String[] args) {
		
		//单线程下访问ArrayList
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(2);
		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			Integer integer = iterator.next();
			if (integer == 2) {
//				list.remove(integer); //修改modCount
				iterator.remove();
			}
		}

		//多个线程同步访问ArrayList
		ArrayList<Integer> scoreList = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {
			scoreList.add(rand.nextInt(100));
		}
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				Iterator<Integer> iter = scoreList.iterator();
				while (iter.hasNext()) {
					System.out.println(iter.next());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Iterator<Integer> iter = scoreList.iterator();
				while (iter.hasNext()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (iter.next() < 60) {
						iter.remove();
					}
				}
			}
		});
		t1.start();
		t2.start();
	}

}
