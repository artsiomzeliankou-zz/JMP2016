package com.epam.jmp.patterns.creational.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

import com.epam.jmp.patterns.creational.Person;

public class PersonImplFS implements IPersonDAO {

	private String fileName = "fileName";
	
	@Override
	public void writePerson(Person person) {
		List<Person> persons = null;

		try (FileInputStream fis = new FileInputStream(fileName); 
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			persons = (List<Person>) ois.readObject();
			if(persons != null){
				persons.add(person);
			}else{
				persons = Arrays.asList(person);
			}

			try (FileOutputStream fout = new FileOutputStream(fileName);
					ObjectOutputStream oos = new ObjectOutputStream(fout)) {
				oos.writeObject(persons);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Person readPerson() {
		Person person = null;
		try (FileInputStream fis = new FileInputStream(fileName); 
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			List<Person> persons = (List<Person>) ois.readObject();
			person = persons.get(persons.size() - 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}

	@Override
	public Person readPerson(String name) {
		Person person = null;
		try (FileInputStream fis = new FileInputStream(fileName); 
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			List<Person> persons = (List<Person>) ois.readObject();
			
			person = persons.stream()
						.filter(target -> target.getName().equals(name))
						.findFirst()
						.get();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}
}