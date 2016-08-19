package multithreading;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread {
	MessageBus messageBus;
	private MessageTag subscriptionTag;

	public Consumer(MessageBus sharedObject, MessageTag messageTag) {
		super("Consumer");
		this.messageBus = sharedObject;
		this.subscriptionTag = messageTag;
	}

	@Override
	public void run() {
		while (true) {
			try {
				int timeRangeMillisFrom = 10;
				int timeRangeMillisTo = 500;
				Thread.sleep(ThreadLocalRandom.current().nextInt(timeRangeMillisFrom, timeRangeMillisTo));
				if (subscriptionTag == messageBus.get()) {
					System.out.println("#" + MessageUtils.getStringMessage(subscriptionTag) + "_Consumer got message");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}