package com.movierentals.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Movie implements CommonModel{

    int id;
    String name;
    boolean rented;
    public int getId() {
        return id;
    }

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

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public String getFile() {
        return "/home/sai/Codebase/sai/codebase/workarea/movierentals/src/main/resources/io/movies.txt";
    }

}
