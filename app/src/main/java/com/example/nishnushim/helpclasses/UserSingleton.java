package com.example.nishnushim.helpclasses;

public class UserSingleton {

    private static UserSingleton instance = new UserSingleton();

    private UserSingleton() {
    }

    public static UserSingleton getInstance(){

        if (instance == null){
            instance = new UserSingleton();
        }

        return instance;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
