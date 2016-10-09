package by.gsu.epamlab.model.beans;

import java.io.File;
import java.io.FileOutputStream;

import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;

public class UploadBean {

	private String fileName;
	private int startInd;
	private int endInd;

	public UploadBean(String fileName, int startInd, int endInd) {
		this.fileName = fileName;
		this.startInd = startInd;
		this.endInd = endInd;
	}

	public void saveFile(byte[] data, String path) throws DaoSystemException {
		if (!fileName.equals(Messages.ERROR_NO_FILE) & fileName != null) {
			File file = new File(path + fileName);
			try {
				FileOutputStream fos = new FileOutputStream(file);
				int length = endInd - startInd;
				fos.write(data, startInd, length);
				fos.close();

			} catch (Exception e) {
				throw new DaoSystemException(Messages.ERROR_WRITE_FILE, e);
			}
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getStartInd() {
		return startInd;
	}

	public void setStartInd(int startInd) {
		this.startInd = startInd;
	}

	public int getEndInd() {
		return endInd;
	}

	public void setEndInd(int endInd) {
		this.endInd = endInd;
	}

	@Override
	public String toString() {
		return "UploadBean [fileName=" + fileName + ", startInd=" + startInd
				+ ", endInd=" + endInd + "]";
	}
}