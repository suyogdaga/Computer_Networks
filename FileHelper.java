package com.cn;

public class FileHelper implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	byte[] content;
	String fileName;
	int receiverNo;
	int senderNo;
	public FileHelper(byte[] content, String fileName,int receiverNo,int senderNo){
		this.content = content;
		this.fileName  = fileName;
		this.receiverNo = receiverNo;
		this.senderNo = senderNo;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getReceiverNo() {
		return receiverNo;
	}
	public void setReceiverNo(int receiverNo) {
		this.receiverNo = receiverNo;
	}
	public int getSenderNo() {
		return senderNo;
	}
	public void setSenderNo(int senderNo) {
		this.senderNo = senderNo;
	}
	
	
}

