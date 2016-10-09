package by.gsu.epamlab.model.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.gsu.epamlab.constants.Constants;

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private int userId;

	private String title;

	private Status status;

	private Date dueDate;

	private String date;

	private String fileName;

	public Task() {
		status = Status.TODO;
	}

	public Task(int id, int userId, String title, Status status, Date dueDate,
			String fileName) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.status = status;
		this.dueDate = dueDate;
		this.fileName = fileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate() {
		SimpleDateFormat outputDateFormat = new SimpleDateFormat(
				Constants.DATE_FORMAT);
		date = outputDateFormat.format(dueDate);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Todo{");
		sb.append("id=").append(id);
		sb.append(", userId=").append(userId);
		sb.append(", title='").append(title).append('\'');
		sb.append(", status=").append(status);
		sb.append(", date=").append(date);
		sb.append('}');
		return sb.toString();
	}

}
