package com.epam.jmp.troubleshooting;

import java.util.HashMap;
import java.util.Map;

public class MemoryLeakProducer1 implements Runnable {

	private Map<Key, String> badlyDesignedMap;
	private int mapSizeFactor;

	MemoryLeakProducer1(int mapSizeFactor) {
		this.mapSizeFactor = mapSizeFactor;
		badlyDesignedMap = new HashMap<Key, String>();
	}

	public void run() {
		fillMapWithDuplicates();
		removeDuplicates(badlyDesignedMap);

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println(Thread.currentThread().getName() + ": Final map size: " + badlyDesignedMap.size());
		}
	}

	private void fillMapWithDuplicates() {
		for (int i = 0; i < mapSizeFactor; i++) {
			fillMap(badlyDesignedMap);
			System.out.println("Filling map iteration " + i + ". Map size: " + badlyDesignedMap.size());
		}
	}

	private void fillMap(Map<Key, String> map) {
		for (int i = 0; i < 10000; i++) {
			Key key = new Key(i);
			if (!map.containsKey(key)) {
				map.put(key, "Number:" + i);
			}
		}
	}

	// useless actions since there is no
	// correct equals() method for Key
	private void removeDuplicates(Map<Key, String> map) {
		for (int i = 0; i < 10000; i++) {
			Key key = new Key(i);
			if (!map.containsKey(key)) {
				map.remove(key);
			}

		}
	}
}
