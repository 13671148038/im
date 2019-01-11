package com.kaiyun.pojo;

public class Task {
	
	private String id;
	private String columnOne;
	private String columnTwo;
	private String columnThree;
	private String columnFour;
	private String type;
	private String createTime;
	private Integer orderColumn;
	private String remark;
	private String wordId;
	
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrderColumn() {
		return orderColumn;
	}
	public void setOrderColumn(Integer orderColumn) {
		this.orderColumn = orderColumn;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getColumnOne() {
		return columnOne;
	}
	public void setColumnOne(String columnOne) {
		this.columnOne = columnOne;
	}
	public String getColumnTwo() {
		return columnTwo;
	}
	public void setColumnTwo(String columnTwo) {
		this.columnTwo = columnTwo;
	}
	public String getColumnThree() {
		return columnThree;
	}
	public void setColumnThree(String columnThree) {
		this.columnThree = columnThree;
	}
	public String getColumnFour() {
		return columnFour;
	}
	public void setColumnFour(String columnFour) {
		this.columnFour = columnFour;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
