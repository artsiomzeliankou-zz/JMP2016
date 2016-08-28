package com.epam.jmp.troubleshooting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Runner {

	public static void main(String[] args) throws InterruptedException, IOException {

		int mapSizeFactor = 50;
		MemoryLeakProducer1 jobA = new MemoryLeakProducer1(mapSizeFactor);
		Thread threadA = new Thread(jobA);
		threadA.setDaemon(true);
		threadA.setName("HashMapKeyLeak");
		threadA.start();
		
		int storageSize = 50000000;
		MemoryLeakProducer2 jobB = new MemoryLeakProducer2(storageSize);
		Thread threadB = new Thread(jobB);
		threadB.setDaemon(true);
		threadB.setName("ThreadLocalLinkLeak");
		threadB.start();
		
		//press [enter] to stop program
		new BufferedReader(new InputStreamReader(System.in)).readLine();
	}
}
