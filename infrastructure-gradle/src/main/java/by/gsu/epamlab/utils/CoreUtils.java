package by.gsu.epamlab.utils;

import by.gsu.epamlab.constants.Constants;

public class CoreUtils {

	/**
	 * A common method for all enums since they can't have another base class
	 * 
	 * @param <T> Enum type
	 * @param c enum of type <T>. All enums must be all caps.
	 * @param string case insensitive
	 * @return corresponding enum, or null
	 */
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c,
			String string) {
		if (c != null && string != null) {
			try {
				return Enum.valueOf(c, string.trim().toUpperCase());
			} catch (IllegalArgumentException ex) {
			}
		}
		return null;
	}
	
	/**
	 * A common method for all enums since they can't have another base class
	 * 
	 * @param e input enum . All enums must be all caps.
	 * @return capitalized string lexical equivalent of enum value, or null 
	 */
	public static String getCapitalizedStringFromEnum(Enum<?> e ) {
        if (e == null) {
            return Constants.KEY_EMPTY;
        }
        String tmp = e.toString();
        int tmpLen = tmp.length();
        return new StringBuilder(tmpLen).append(tmp.charAt(0))
                .append(tmp.substring(1).toLowerCase()).toString();
    }
	
	/**
	 * A common method for all strings
	 * 
	 * @param inputString the String to capitalize, no matter register of each letter
	 * @return capitalized string lexical equivalent of inputString String, or null string
	 */
	public static String getCapitalizedStringFromString(String inputString ) {
        int inputStringLen = inputString.length();
        if (inputStringLen == 0) {
            return inputString;
        }
        return new StringBuilder(inputStringLen).append(inputString.charAt(0))
                .append(inputString.substring(1).toLowerCase()).toString();
    }
}
