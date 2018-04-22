package com.mteam.android_professional.anninhmangotp;

/**
 * Created by Stealer Of Souls on 4/22/2018.
 */

public class User {
    private String email;
    private String pass;
    private String phone;

    public User(String email, String pass, String phone) {
        this.email = email;
        this.pass = pass;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
