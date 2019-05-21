package com.movierentals.model;

import org.springframework.stereotype.Component;

@Component
public class Client implements CommonModel{

    public int getId(String name) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    int id;
    String name;
    boolean deleted;

    public String getFile() {
        return "/home/sai/Codebase/sai/codebase/workarea/movierentals/src/main/resources/io/clients.txt";
    }
}
