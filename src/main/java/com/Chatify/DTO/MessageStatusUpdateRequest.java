package com.Chatify.DTO;

public class MessageStatusUpdateRequest {
private Long messageId;
    public Long getMessageId() {
    return messageId;
}
public void setMessageId(Long messageId) {
    this.messageId = messageId;
}
public Long getUserId() {
    return userId;
}
public void setUserId(Long userId) {
    this.userId = userId;
}
    private Long userId;
}
