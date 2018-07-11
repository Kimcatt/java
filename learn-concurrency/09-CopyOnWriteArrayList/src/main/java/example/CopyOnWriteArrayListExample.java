package example;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWrite并发容器用于读多写少的并发场景。比如白名单，黑名单。
 * 
 * @date 2018.7.11
 */
public class CopyOnWriteArrayListExample {
	
	private CopyOnWriteArrayList<ObjectWrapper<Integer>> list = new CopyOnWriteArrayList<>();

	private int n = 16;
	
	public static void main(String[] args) {
		CopyOnWriteArrayListExample example = new CopyOnWriteArrayListExample();
		example.launchPrintingThread();
		example.loadData();
		
	}
	
	/**
	 * 填装数据
	 */
	public void loadData() {
		for (int i = 0; i < 16; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list.add(new ObjectWrapper<Integer>(i * i));
		}
//		list.remove(0);
	}
	
	
	/**
	 * 启动打印线程
	 */
	public void launchPrintingThread() {
		new Thread () {
			@Override
			public void run() {
				for (int i = 0; i < 17; i++) {
					System.out.println(Arrays.toString(list.toArray()).replaceAll("\\[|\\]", ""));
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}

class ObjectWrapper<T> {
	private T innerObject;

	public ObjectWrapper(T innerObject) {
		this.innerObject = innerObject;
	}

	@Override
	public String toString() {
		return String.format("{data: \"%s\", hash: \"%s\"}", innerObject, this.hashCode());
	}
	
}
