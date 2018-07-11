package example;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueBasedProducerAndConsumerExample {

	private int queueSize = 10;
	
	private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueSize);
	
	public static void main(String[] args) {
		BlockingQueueBasedProducerAndConsumerExample example = new BlockingQueueBasedProducerAndConsumerExample();
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
			while (true) {
				try {
					Thread.sleep((long) Math.floor(Math.random() * 100));
					queue.put(1);
					System.out.format("生产了一个元素，剩余%s个空位%n", (queueSize - queue.size()));
				} catch (InterruptedException e) {
					e.printStackTrace();
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
					Thread.sleep((long) Math.floor(Math.random() * 100));
					queue.take();
					System.out.format("消费了一个元素，剩余%s个元素%n", queue.size());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
