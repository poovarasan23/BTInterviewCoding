package com.exam.bt.dto;

import com.exam.bt.util.enums.Role;

public class UserSessionDTO {
    private String userName;
    private Role Role;
    private String msg;

    public UserSessionDTO() {
    }

    public UserSessionDTO(String userName, Role role, String msg) {
        this.userName = userName;
        this.Role = role;
        this.msg = msg;
    }

    public UserSessionDTO(String msg) {
        this.msg = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Role getRole() {
        return Role;
    }

    public void setRole(Role role) {
        Role = role;
    }
}
