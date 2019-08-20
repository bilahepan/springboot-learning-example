package org.spring.springboot.blogs.demo;

import java.io.Serializable;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/20 上午7:36
 */
public class ClassDemo {
    public static UserDTO user1 = new UserDTO("1");
    private static UserDTO user2 = new UserDTO("2");

    public static void main(String[] args) {
        System.out.println(user1.getClass().equals(user2.getClass()));
        //结果是 true
    }

    public static class UserDTO implements Serializable {
        private String userId;
        public UserDTO(String userId) {this.userId = userId; }

        public String getUserId() {return userId; }
        public void setUserId(String userId) {this.userId = userId; }

        @Override
        public String toString() {
            return "UserDTO{" +
                    "userId='" + userId + '\'' +
                    '}';
        }
    }
}