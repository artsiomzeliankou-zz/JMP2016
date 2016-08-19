package multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageBus {

	private int capacity;
	private final Queue<MessageTag> queue = new LinkedList<MessageTag>();

	Lock lock = new ReentrantLock();
	private final Condition bufferNotFull = lock.newCondition();
	private final Condition bufferNotEmpty = lock.newCondition();

	MessageBus(int capacity) {
		this.capacity = capacity;
	}

	public void put(MessageTag messageTag) throws InterruptedException {
		lock.lock();
		try {
			while (queue.size() == capacity) {
				bufferNotEmpty.await();
			}
			boolean isAdded = queue.offer(messageTag);
			if (isAdded) {
				System.out.println("New message with #" + MessageUtils.getStringMessage(messageTag) + " tag."
						+ " Messages in queue:" + queue.size());
				bufferNotFull.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

	public MessageTag get() throws InterruptedException {
		lock.lock();
		MessageTag valueFromQueue = null;
		try {
			while (queue.size() == 0) {
				bufferNotFull.await();
			}
			valueFromQueue = queue.peek();
			if (valueFromQueue != null) {
				valueFromQueue = queue.poll();
				bufferNotEmpty.signalAll();
			}
		} finally {
			lock.unlock();
		}
		return valueFromQueue;
	}

}
