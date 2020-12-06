package com.example.duolingo;

public class UserDB {

    int id;
    String name;

    public UserDB() {}

    public UserDB(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
