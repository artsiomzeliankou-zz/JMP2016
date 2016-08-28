package com.epam.jmp.troubleshooting;

public class MemoryLeakProducer2 implements Runnable {

	private int storageSize;
	private StorageContext storageContext;

	public MemoryLeakProducer2(int storageSize) {
		this.storageSize = storageSize;
		this.storageContext = new StorageContext();
	}

	public void run() {
		if (storageContext.getSx() == null) {
			storageContext.setSx(new Storage(storageSize));
		}
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println(
					Thread.currentThread().getName() + ": Storage sum: " + storageContext.getSx().getStorageSum());
		}
	}

}
