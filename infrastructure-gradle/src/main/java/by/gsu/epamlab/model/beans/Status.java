package by.gsu.epamlab.model.beans;

import by.gsu.epamlab.utils.CoreUtils;

public enum Status {
    TODO,
    FIXED,
    REMOVED;
    
    public static Status fromString(String name) {
		return CoreUtils.getEnumFromString(Status.class, name);
	}
}
