package multithreading;

import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {
	private MessageBus messageBus;
	public Producer(MessageBus sharedObject) {
		super("Producer");
		this.messageBus = sharedObject;
	}

	@Override
	public void run() {
		while (true) {
			try {
				int timeRangeMillisFrom = 10;
				int timeRangeMillisTo = 500;
				Thread.sleep(ThreadLocalRandom.current().nextInt(timeRangeMillisFrom, timeRangeMillisTo));
				MessageTag messageTag = MessageUtils.generateMessageTag();
				messageBus.put(messageTag);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
