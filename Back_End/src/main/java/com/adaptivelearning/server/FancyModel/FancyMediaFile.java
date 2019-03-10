package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.MediaFile;

public class FancyMediaFile {
	
private long fileId;
private String fileName;
private String fileType;
private byte[] data;
private String fileDownloadUri;
private long size;

public FancyMediaFile() {
}
 
public FancyMediaFile toFancyFileMapping (MediaFile file) {
	this.fileId=file.getFileId();
	this.fileName=file.getFileName();
	this.fileType=file.getFileType();
	this.fileDownloadUri=file.getFileDownloadUri();
	this.size=file.getSize();
	return this;
}

public long getFileId() {
	return fileId;
}
public void setFileId(long fileId) {
	this.fileId = fileId;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getFileType() {
	return fileType;
}
public void setFileType(String fileType) {
	this.fileType = fileType;
}
public byte[] getData() {
	return data;
}
public void setData(byte[] data) {
	this.data = data;
}
public String getFileDownloadUri() {
	return fileDownloadUri;
}
public void setFileDownloadUri(String fileDownloadUri) {
	this.fileDownloadUri = fileDownloadUri;
}
public long getSize() {
	return size;
}
public void setSize(long size) {
	this.size = size;
}

}
