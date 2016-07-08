package dry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsDAOImpl implements StudentsDAO {
	@Override
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<>();
		try (Connection connection = JdbcUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STUDENTS");
			ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					students.add(getStudentFromResultSet(resultSet));
				}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return students;
	}

	private Student getStudentFromResultSet(ResultSet resultSet) throws SQLException {
		Student student = new Student();
		student.setId(resultSet.getLong("ID"));
		student.setName(resultSet.getString("NAME"));
		student.setAge(resultSet.getInt("AGE"));
		return student;
	}

    @Override
	public Student getStudent(long id) {
		Student student = null;
		try (Connection connection = JdbcUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE ID = ?")) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					student = getStudentFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return student;
	}

    @Override
	public void saveStudent(Student student) {
		try (Connection connection = JdbcUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STUDENTS SET NAME = ?, AGE = ? WHERE ID = ?")) {
			preparedStatement.setString(1, student.getName());
			preparedStatement.setInt(2, student.getAge());
			preparedStatement.setLong(3, student.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
	public List<Student> getStudentsByAge(int from, int to) {
		List<Student> students = new ArrayList<>();
		try (Connection connection = JdbcUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE AGE BETWEEN ? AND ?")) {
			preparedStatement.setInt(1, from);
			preparedStatement.setInt(2, to);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Student student = getStudentFromResultSet(resultSet);
					students.add(student);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return students;
	}
}