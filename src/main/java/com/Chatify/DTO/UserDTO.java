package com.Chatify.DTO;

public class UserDTO {

        private Long userId;
        private String username;
        private String avatarUrl;

    
        public Long getUserId() {
            return userId;
        }
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getAvatarurl() {
            return avatarUrl;
        }
        public void setAvatarurl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
}
