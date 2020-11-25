package com.example.duolingo;

public class Data {

    public int id;
    public int level_id;
    public String source;
    public String name;

    public Data(){}

    public Data(int id, int level_id, String source, String name) {
        this.id = id;
        this.level_id = level_id;
        this.source = source;
        this.name = name;
    }
}
