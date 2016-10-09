package by.gsu.epamlab.utils;

import by.gsu.epamlab.constants.SqlRequests;

/**
 * Enumeration for receiving SQL request from string day.
 */
public enum ActiveTaskDecoder {

	TODAY(SqlRequests.SELECT_TODAY_TASKS), 
	TOMORROW(SqlRequests.SELECT_TOMORROW_TASKS), 
	SOMEDAY(SqlRequests.SELECT_SOMEDAY_TASKS);

	private String sqlRequest;

	ActiveTaskDecoder(String sqlRequest) {
		this.sqlRequest = sqlRequest;
	}

	public String getSqlStrinq() {
		return sqlRequest;
	}

	public static ActiveTaskDecoder fromString(String name) {
		return CoreUtils.getEnumFromString(ActiveTaskDecoder.class, name);
	}

}
