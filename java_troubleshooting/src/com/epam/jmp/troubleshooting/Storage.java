package com.epam.jmp.troubleshooting;

public class Storage {

	private long[] myStorage;

	Storage(int storageSize) {
		myStorage = new long[storageSize];
		fillStorage();
	}
	
	public long[] getStorage(){
		return this.myStorage;
	}
	
	private void fillStorage(){
		for (int i = 0; i < myStorage.length; i ++){
			myStorage[i] = i;
		}
	}
	
	public long getStorageSum(){
		long sum = 0;
		for (int i = 0; i < myStorage.length; i ++){
			sum += i;
		}
		return sum;
	}
}
