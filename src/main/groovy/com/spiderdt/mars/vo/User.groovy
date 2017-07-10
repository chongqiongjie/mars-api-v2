package com.spiderdt.mars.vo

/**
 *  Created by kun on 2017/7/10.
 */

public class User {
    private String username
    private String password

    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    User(getUsername, getPassword) {
        username = getUsername
        password = getPassword
    }

    void setPassword(String password) {
        this.password = password
    }

    void setUsername(String username) {
        this.username = username
    }
}

