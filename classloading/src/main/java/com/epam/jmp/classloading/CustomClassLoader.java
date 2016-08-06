package com.epam.jmp.classloading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

public class CustomClassLoader extends ClassLoader {
	private static Logger logger = Logger.getLogger(CustomClassLoader.class);
	private final HashMap<String, Class<?>> customCache = new HashMap<String, Class<?>>();
	private final String path;
	private final String fileName;

	public CustomClassLoader(String jarName, String path) {
		this.fileName = jarName;
		this.path = path;
	}

	@Override
	public synchronized Class<?> loadClass(String className) throws ClassNotFoundException {
		if (customCache.containsKey(className)) {
			return (Class<?>) customCache.get(className);
		}
		Class<?> clazz = findLoadedClass(className);
		try {
			if (clazz == null) {
				clazz = super.loadClass(className);
			}
		} catch (ClassNotFoundException e) {
			logger.info("Class not found in custom cache, parent ClassLoader will proceed search");
		}
		if (clazz == null) {
			String pathName = path + File.separator + fileName;
			try (JarFile jar = new JarFile(new File(pathName))){
				String jarEntryName = normalize(className);
				JarEntry jarEntry = jar.getJarEntry(jarEntryName);
				if (jarEntry == null) {
					throw new ClassNotFoundException("Class not found " + className);
				}
				byte[] classData = loadClassData(jar, jarEntry);
				if (classData != null) {
					clazz = defineClass(className, classData, 0, classData.length);
					customCache.put(className, clazz);
					resolveClass(clazz);
					logger.info("Class " + clazz.getName() + " loaded successfully");
				}
			} catch (IOException e) {
				logger.error("JarFile " + path + fileName + " not found");
			}
		}
		return clazz;
	}

	private String normalize(String className) {
		return className.replace('.', '/') + ".class";
	}

	private byte[] loadClassData(JarFile jarFile, JarEntry jarEntry) throws IOException {
		long size = jarEntry.getSize();
		if ((size == -1L) || (size == 0L)) {
			return null;
		}
		byte[] data = new byte[(int) size];
		InputStream in = jarFile.getInputStream(jarEntry);
		in.read(data);
		return data;
	}

	@Override
	public String toString() {
		return CustomClassLoader.class.getName();
	}
}
