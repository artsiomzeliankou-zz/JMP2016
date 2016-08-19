package multithreading;


public class SingleParticipantModeRunner {

	public static void main(String[] args) {

		int messageBusCapacity = 10;
		int programDurationMillis = 5000;
		MessageBus messageBus = new MessageBus(messageBusCapacity);

		Producer p = new Producer(messageBus);
		Consumer c = new Consumer(messageBus, MessageUtils.generateMessageTag());
		p.setDaemon(true);
		c.setDaemon(true);
		p.start();
		c.start();

		Thread.currentThread();
		try {
			Thread.sleep(programDurationMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}