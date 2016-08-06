package com.epam.jmp.classloading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class Runner {

	private static Logger logger = Logger.getLogger(Runner.class);
    private final static String END_KEYWORD = "stop";

    public static void main(String[] args) {
        Runner runner = new Runner();
        boolean runAgain = true;
        logger.info("Let's start application running!");
        logger.info("For terminate application running enter in any field this text: "
                + "\"" + END_KEYWORD + "\"");
        while (runAgain) {
            try {
                runAgain = runner.process();
            } catch (ClassNotFoundException | IOException e) {
                logger.error(e);
            }
        }
        logger.info("Terminated!");

    }

    private boolean process() throws IOException, ClassNotFoundException {
        boolean runAgain = true;
        String[] parameters = readValues();
        for (int i = 0; i < parameters.length; i++) {
            if (!hasText(parameters[i])) {
                return runAgain;
            }
            if (END_KEYWORD.equalsIgnoreCase(parameters[i])) {
                return false;
            }

        }
        String jarName = parameters[0];
        String path = parameters[1];
        String className = parameters[2];
        CustomClassLoader loader = new CustomClassLoader(jarName, path);
        Class<?> clazz = loader.loadClass(className);
        if(clazz != null){
        	logger.info("\n Class " + clazz.getName() + " successfully loaded, let's start again");
        }else{
        	logger.info("Class not found, let's start again \n");
        }
        return runAgain;
    }

    private String[] readValues() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        String[] values = new String[3];
        try {
            logger.info("Enter jar file name");
            values[0] = reader.readLine();
            logger.info("Enter file location path");
            values[1] = reader.readLine();
            logger.info("Enter full class name");
            values[2] = reader.readLine();
        } catch (IOException e) {
            logger.error(e);
        }
        return values;

    }

    private static boolean hasText(String string) {
        CharSequence str = (CharSequence) string;
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

}
