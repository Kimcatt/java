package example;

/**
 * 每个线程Thread包含一个ThreadLocal.ThreadLocalMap类型的成员变量threadLocals
 * 这个成员变量以ThreadLocal<?>为键
 * 
 * 注：ThreadLocal变量在调用get之前需事先调用set方法设置值，或者重写ThreadLocal<?>类型的initialValue方法
 * 
 * @date 2018.7.9
 */
public class Test {
	
	ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
	
	ThreadLocal<String> stringLocal = new ThreadLocal<String>();
	
	ThreadLocal<Long> longLocalExt = new ThreadLocal<Long>(){
        protected Long initialValue() {
            return Thread.currentThread().getId();
        };
    };
    ThreadLocal<String> stringLocalExt = new ThreadLocal<String>(){;
        protected String initialValue() {
            return Thread.currentThread().getName();
        };
    };

	public void set() {
		longLocal.set(Thread.currentThread().getId());
		stringLocal.set(Thread.currentThread().getName());
	}

	public long getLong() {
//		return longLocal.get();
		return longLocalExt.get();
	}

	public String getString() {
//		return stringLocal.get();
		return stringLocalExt.get();
	}

	public static void main(String[] args) throws InterruptedException {
		final Test test = new Test();

		test.set(); // 调用get前需先调用set, 或者重写initialValue方法
		System.out.println(test.getLong());
		System.out.println(test.getString());

		Thread t = new Thread() {
			public void run() {
//				test.set();
				System.out.println(test.getLong());
				System.out.println(test.getString());
			};
		};
		t.start();
		t.join();

		System.out.println(test.getLong());
		System.out.println(test.getString());
	}
}
