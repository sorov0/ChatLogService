package com.chatlogservice.chatlogservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserMessage {
	
	@Id
	@GeneratedValue
	private String messageID;
	private long timeStamp;
	private String messageText;
	private boolean isSent;
	private String userID;
	
	public UserMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public UserMessage(String messageID, Long timeStamp, String messageText, boolean isSent , String userID) {
		super();
		this.messageID = messageID;
		this.timeStamp = timeStamp;
		this.messageText = messageText;
		this.isSent = isSent;
		this.userID = userID;
	}



	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public String getUserID() {
		return userID;
	}
	
	
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public boolean isSent() {
		return isSent;
	}
	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}



	@Override
	public String toString() {
		return "User [messageID=" + messageID + ", timeStamp=" + timeStamp + ", messageText=" + messageText
				+ ", isSent=" + isSent + "]";
	}

}
