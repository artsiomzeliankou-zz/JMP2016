package com.epam.jmp.troubleshooting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExampleRunner {

	public static void main(String[] args) {

		// Lists with 10 String objets
		String[] arr = new String[] { "test 1", "test 2", "test 3", "test 4", "test 5", "test 6", "test 7", "test 8",
				"test 9", "test 10" };
		List<String> multiStringArrayList = new ArrayList<String>();
		List<String> multiStringLinkedList = new LinkedList<String>();
		for (int i = 0; i < arr.length; i++) {
			multiStringArrayList.add(arr[i]);
			multiStringLinkedList.add(arr[i]);
		}

		// Lists with 10 Integer objets
		List<Integer> multiIntArrayList = new ArrayList<Integer>();
		List<Integer> multiIntLinkedList = new LinkedList<Integer>();
		for (int i = 0; i < arr.length; i++) {
			multiIntArrayList.add(i);
			multiIntLinkedList.add(i);
		}

		// Lists with single String objets
		List<String> singleStringArrayList = new ArrayList<String>(1);
		singleStringArrayList.add(new String("test singleStringArrayList"));
		List<String> singleStringLinkedList = new LinkedList<String>();
		singleStringLinkedList.add(new String("test singleStringLinkedList"));

		
		// Lists with single Integer objets
		List<Integer> singleIntArrayList = new ArrayList<Integer>(1);
		singleIntArrayList.add(new Integer(42));
		List<Integer> singleIntLinkedList = new LinkedList<Integer>();
		singleIntLinkedList.add(new Integer(42));

		// press [enter] to stop program
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			Thread.dumpStack();
			throw new RuntimeException(e);
		}

	}

}
