package com.kaiyun.pojo;

public class Word {
	
	private String id;
	private String importTime;
	
	public Word(String id, String importTime) {
		this.id = id;
		this.importTime = importTime;
	}
	public Word() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImportTime() {
		return importTime;
	}
	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}
	

}
