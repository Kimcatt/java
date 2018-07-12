package example;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition创建生产者消费者示例
 * 
 * @date 2018.7.12
 */
public class ConditionBasedProducerAndConsumerExample {
	
	private final int queueSize = 10;
	
	private Queue<Integer> queue = new PriorityQueue<>();
	
	private Lock lock = new ReentrantLock();
	
	private Condition notFull = lock.newCondition();
	
	private Condition notEmpty = lock.newCondition();

	public static void main(String[] args) {
		ConditionBasedProducerAndConsumerExample example = new ConditionBasedProducerAndConsumerExample();
		
		Producer producer = example.new Producer();
		Consumer consumer = example.new Consumer();
		
		producer.start();
		consumer.start();
	}

	class Producer extends Thread {
		private int sleepDuration = 3000;
		
		@Override
		public void run() {
			produce();
		}

		private void produce() {
			while(true) {
				try {
					Thread.sleep((long) (Math.random() * sleepDuration));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.lock();
				try {
					while (queue.size() == queueSize) {
						System.out.println("队列满, 等待剩余空间");
						try {
							notFull.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 
					queue.offer(1);
					notEmpty.signal();
					System.out.format("生产者%s生产了一件商品, 剩余%s个空间%n", Thread.currentThread().getName(),
							queueSize - queue.size());
				} finally {
					lock.unlock();
				}
			}
		}
	}
	
	class Consumer extends Thread {
		
		private int sleepDuration = 10000;
		
		@Override
		public void run() {
			consume();
		}

		private void consume() {
			while(true) {
				try {
					Thread.sleep((long) (Math.random() * sleepDuration));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.lock();
				
				try {
					while (queue.isEmpty()) {
						try {
							notEmpty.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 
					queue.poll();
					notFull.signal();
					System.out.format("消费者%s消费了一件商品, 队列剩余%s个商品%n", Thread.currentThread().getName(),
							queue.size());
				} finally {
					lock.unlock();
				}
			}
		}
	}
	
}
