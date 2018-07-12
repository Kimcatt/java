package example;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 使用Object.wait()和Object.notify()实现生产者和消费者
 * 
 * @date 2018.7.12
 */
public class WaitAndNotifyBasedProducerAndConsumerExample {

	private final int queueSize = 10;

	private Queue<Integer> queue = new PriorityQueue<>(queueSize);

	public static void main(String[] args) {
		WaitAndNotifyBasedProducerAndConsumerExample example = new WaitAndNotifyBasedProducerAndConsumerExample();
		Producer producer = example.new Producer();
		Consumer consumer = example.new Consumer();
		producer.start();
		consumer.start();
	}

	class Producer extends Thread {
		
		private final int duration = 3000;

		@Override
		public void run() {
			produce();
		}

		private void produce() {
			while (true) {
				try {
					Thread.sleep((long) (Math.random() * duration));
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				synchronized (queue) {
					while (queue.size() == queueSize) {
						try {
							System.out.println("队列满，等待剩余空间");
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							queue.notify();
						}
					}
					boolean success = queue.offer(1);
					queue.notify();
					System.out.format("生产者%s生产了一件商品，商品入队%s, 队列剩余%s个空间%n", Thread.currentThread().getName(),
							success ? "成功" : "失败", queueSize - queue.size());
				}
			}
		}
	}

	class Consumer extends Thread {
		
		private final int duration = 5000;
		
		@Override
		public void run() {
			consume();
		}

		private void consume() {
			while (true) {
				try {
					Thread.sleep((long) (Math.random() * duration));
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							System.out.println("队列空，等待商品");
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							queue.notify();
						}
					}
					queue.poll();
					queue.notify();
					System.out.format("消费者%s消费了一件商品, 剩余%s件商品%n", Thread.currentThread().getName(), queue.size());
				}
			}
		}
	}

}
