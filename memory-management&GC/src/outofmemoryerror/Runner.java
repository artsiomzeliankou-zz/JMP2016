package outofmemoryerror;

public class Runner {

	public static void main(String[] args) throws InterruptedException {

		Thread.currentThread().setName("Main thread");
		
		produceOutOfMemoryError();

	}

	/**
	 * Method shows how to reach java.lang.OutOfMemoryError: Java heap space by
	 * 2-ways
	 * 
	 * tested with -Xmx jvm parameter up to 4096m
	 */
	private static void produceOutOfMemoryError() throws InterruptedException {

		final int itemSize = 50000000;// approx 100Mb object in regard to
										// StringBuilder(int capacity)
										// constructor

		// 1. See java.lang.OutOfMemoryError for parallel threads
		for (int i = 0; i < 50; i++) {
			Thread.sleep(100);
			Thread worker = new Thread(new HeapConsumer(itemSize));
			worker.setName("Parallel thread_" + i);
			worker.setDaemon(true);
			worker.start();
			printHeapSize();
		}

		// 2. See jdva.lang.OutOfMemoryError for a single thread
		Item mainItem = new Item(itemSize);
		for (int i = 0; i < 50; i++) {
			Thread.sleep(100);
			mainItem.setValue(((StringBuilder) mainItem.getValue()).append(new StringBuilder(itemSize).toString()));
			printHeapSize();
		}
	}

	private static void printHeapSize() {
		Runtime runtime = Runtime.getRuntime();
		System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + " Mb");
	}

}

class HeapConsumer implements Runnable {

	private int itemSize;

	public HeapConsumer(int itemSize) {
		this.itemSize = itemSize;
	}

	public void run() {
		Item item = new Item(itemSize);
		while (true) {
			item.getValue();
		}
	}
}

class Item {

	private Object value;

	public Item(int itemSize) {
		this.value = new StringBuilder(itemSize);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}