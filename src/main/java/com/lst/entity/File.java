package com.lst.entity;

import java.util.Arrays;

public class File {
	private String fileName;
	private byte[] file;
	private String type;
	private int fileSize;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		return "File [fileName=" + fileName + ", file=" + Arrays.toString(file) + ", type=" + type + ", fileSize="
				+ fileSize + "]";
	}
	
}
