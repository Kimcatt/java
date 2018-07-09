package example;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 同步容器，多线程访问须谨慎处理
 * 
 * @date 2018.7.9
 */
public class Test {

	public static void main(String[] args) throws InterruptedException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Vector<Integer> vector = new Vector<Integer>();
		int n = 1000000;
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			list.add(i);
		long end = System.currentTimeMillis();
		System.out.println("ArrayList进行" + n + "次插入操作耗时：" + (end - start) + "ms");
		start = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			vector.add(i);
		end = System.currentTimeMillis();
		System.out.println("Vector进行" + n + "次插入操作耗时：" + (end - start) + "ms");
		
		synchronizedContainerTest(null);
	}

	static Vector<Integer> vector = new Vector<Integer>();

	public static void synchronizedContainerTest(String[] args) throws InterruptedException {
		while (true) {
			for (int i = 0; i < 10; i++)
				vector.add(i);
			Thread t1 = new Thread() {
				public void run() {
//					Iterator<Integer> iterator = vector.iterator();
//					while (iterator.hasNext())
//						iterator.remove();
					synchronized (Test.class) {
						for(int i=0;i<vector.size();i++)
							vector.remove(i);
					}
				};
			};
			Thread t2 = new Thread() {
				public void run() {
//					Iterator<Integer> iterator = vector.iterator();
//					while (iterator.hasNext()) {
//						iterator.next();
//					}
					synchronized (Test.class) {
						for(int i=0;i<vector.size();i++)
							vector.get(i);
					}
				};
			};
			t1.start();
			t2.start();
			while (Thread.activeCount() > 10) {

			}
		}
	}
}
