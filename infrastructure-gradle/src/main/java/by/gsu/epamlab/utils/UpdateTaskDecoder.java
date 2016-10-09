package by.gsu.epamlab.utils;

import by.gsu.epamlab.constants.SqlRequests;

/**
 * Enumeration for receiving SQL request from string update type.
 */
public enum UpdateTaskDecoder {

	FIX(SqlRequests.FIX_TASK), 
	REMOVE(SqlRequests.REMOVE_TASK), 
	READD(SqlRequests.READD_TASK),
	DELETE(SqlRequests.DELETE_TASK);

	private String sqlRequest;

	UpdateTaskDecoder(String sqlRequest) {
		this.sqlRequest = sqlRequest;
	}

	public String getSqlStrinq() {
		return sqlRequest;
	}

	public static UpdateTaskDecoder fromString(String name) {
		return CoreUtils.getEnumFromString(UpdateTaskDecoder.class, name);
	}

}
