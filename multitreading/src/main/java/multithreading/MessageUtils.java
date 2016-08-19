package multithreading;

import java.util.concurrent.ThreadLocalRandom;

public class MessageUtils {

	public static MessageTag generateMessageTag() {
		int tagIndex = ThreadLocalRandom.current().nextInt(1, 3 + 1);
		switch (tagIndex) {
		case 1:
			return MessageTag.NEWS;
		case 2:
			return MessageTag.CARS;
		case 3:
			return MessageTag.SPORT;
		default:
			
			return MessageTag.NEWS;
		}
	}
	
	public static String getStringMessage(MessageTag messageTag ) {
        if (messageTag == null) {
            return "";
        }
        String tmp = messageTag.toString();
        int tmpLen = tmp.length();
        return new StringBuilder(tmpLen).append(tmp.charAt(0))
                .append(tmp.substring(1).toLowerCase()).toString();
    }
}
