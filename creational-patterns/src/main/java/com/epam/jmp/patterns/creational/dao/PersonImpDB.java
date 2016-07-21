package com.epam.jmp.patterns.creational.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.jmp.patterns.creational.JdbcUtils;
import com.epam.jmp.patterns.creational.Person;

public class PersonImpDB implements IPersonDAO {

	@Override
	public void writePerson(Person person) {
		try (Connection connection = JdbcUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PERSONS VALUES(?, ?, ?)")) {
			preparedStatement.setLong(1, person.getId());
				preparedStatement.setString(2, person.getName());
				preparedStatement.setInt(3, person.getAge());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public Person readPerson() {
		Person person = null;
		try (Connection connection = JdbcUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSONS ORDER BY ID DESC LIMIT 1");
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				person = getPersonFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return person;
	}

	@Override
	public Person readPerson(String name) {
		Person person = null;
		try (Connection connection = JdbcUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSONS WHERE NAME = ?")) {
			preparedStatement.setString(1, name);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					person = getPersonFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return person;
	}

	private Person getPersonFromResultSet(ResultSet resultSet) throws SQLException {
		Person person = new Person();
		person.setId(resultSet.getLong("ID"));
		person.setName(resultSet.getString("NAME"));
		person.setAge(resultSet.getInt("AGE"));
		return person;
	}
}
