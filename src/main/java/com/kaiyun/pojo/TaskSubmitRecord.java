package com.kaiyun.pojo;

public class TaskSubmitRecord {
	
	private Integer id;
	private String submitUser;
	private String taskId;
	private String submitTime;
	private String checkTime;
	private String taskType;
	private String checkResult;
	
	public TaskSubmitRecord() {
	}
	
	public TaskSubmitRecord(String submitUser, String taskId, String submitTime, String checkTime, String taskType,
			String checkResult) {
		this.submitUser = submitUser;
		this.taskId = taskId;
		this.submitTime = submitTime;
		this.checkTime = checkTime;
		this.taskType = taskType;
		this.checkResult = checkResult;
	}

	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
}
