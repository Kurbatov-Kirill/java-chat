package logic;

import java.time.LocalDateTime;

public class Message {
	private int messageId;
	private int messageSenderId;
	private int messageReceiverId;
	private String messageContent;
	private LocalDateTime messageSendTimestamp;
	private LocalDateTime messageCheckedTimestamp;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getMessageSenderId() {
		return messageSenderId;
	}
	public void setMessageSenderId(int messageSenderId) {
		this.messageSenderId = messageSenderId;
	}
	public int getMessageReceiverId() {
		return messageReceiverId;
	}
	public void setMessageReceiverId(int messageReceiverId) {
		this.messageReceiverId = messageReceiverId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public LocalDateTime getMessageSendTimestamp() {
		return messageSendTimestamp;
	}
	public void setMessageSendTimestamp(LocalDateTime messageSendTimestamp) {
		this.messageSendTimestamp = messageSendTimestamp;
	}
	public LocalDateTime getMessageCheckedTimestamp() {
		return messageCheckedTimestamp;
	}
	public void setMessageCheckedTimestamp(LocalDateTime messageCheckedTimestamp) {
		this.messageCheckedTimestamp = messageCheckedTimestamp;
	}
	
}
