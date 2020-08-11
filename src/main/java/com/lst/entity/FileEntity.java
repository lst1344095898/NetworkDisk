package com.lst.entity;


public class FileEntity {
	private int fileid;
	private String fileName;
	private String filePath;
	private String type;
	private int fileSize;
	private int userid;
	public int getFileid() {
		return fileid;
	}
	public void setFileid(int fileid) {
		this.fileid = fileid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "FileEntity [fileid=" + fileid + ", fileName=" + fileName + ", filePath=" + filePath + ", type=" + type
				+ ", fileSize=" + fileSize + ", userid=" + userid + "]";
	}


	
}
