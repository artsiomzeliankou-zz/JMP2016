package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiParticipantModeRunner {

	public static void main(String[] args) {

		//Never Ending program!
		int messageBusCapacity = 10;
		MessageBus messageBus = new MessageBus(messageBusCapacity);

		ExecutorService ex = Executors.newCachedThreadPool();
		
		Producer p1 = new Producer(messageBus); 
		Producer p2 = new Producer(messageBus);
		Producer p3 = new Producer(messageBus);
		Consumer c1 = new Consumer(messageBus, MessageUtils.generateMessageTag());
		Consumer c2 = new Consumer(messageBus, MessageUtils.generateMessageTag());
		Consumer c3 = new Consumer(messageBus, MessageUtils.generateMessageTag());
		
		ex.execute(p1);
		ex.execute(p2);
		ex.execute(p3);
		ex.execute(c1);
		ex.execute(c2);
		ex.execute(c3);
	}
}
