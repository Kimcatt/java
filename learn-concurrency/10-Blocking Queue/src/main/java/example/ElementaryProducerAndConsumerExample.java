package example;

import java.util.PriorityQueue;

/**
 * 基本的生产者消费者示例
 * 
 * @date 2018.7.11
 */
public class ElementaryProducerAndConsumerExample {
	
	private int queueSize = 10;
	
	private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);
	

	public static void main(String[] args) {
		ElementaryProducerAndConsumerExample example = new ElementaryProducerAndConsumerExample();
		Producer producer = example.new Producer();
		Consumer consumer = example.new Consumer();
		producer.start();
		consumer.start();
	}
	
	
	class Producer extends Thread {
		@Override
		public void run() {
			produce();
		}

		private void produce() {
			while(true) {
				try {
					Thread.sleep((long) Math.floor(Math.random() * 1000));
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				synchronized (queue) {
					while (queue.size() == queueSize) {
						System.out.println("队列满，等待剩余空间");
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							queue.notify();
						}
					}
					queue.offer(1);
					queue.notify();
					System.out.format("插入一个元素，剩余%s个空位%n", queueSize - queue.size());
				}
			}
		}
	}
	
	class Consumer extends Thread {
		@Override
		public void run() {
			consume();
		}

		private void consume() {
			while(true) {
				try {
					Thread.sleep((long) Math.floor(Math.random() * 1000));
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				synchronized (queue) {
					while (queue.isEmpty()) {
						System.out.println("队列空，等待数据");
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							queue.notify();
						}
					}
					queue.poll();
					queue.notify();
					System.out.format("取出一个元素，剩余%s个元素%n", queue.size());
				}
			}
		}
	}

}
