package com.movierentals.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class RentalInfo{

    private String client;
    private String movie;
    private String inDate;

    private String outDate;
    private int id;

    public String getFile() {
        return "/home/sai/Codebase/sai/codebase/workarea/movierentals/src/main/resources/io/rental_info.txt";
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        this.outDate=dtf.format(localDate);
        return outDate;
    }


    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }


   /* public void setFile(String file) {
        this.file = file;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        return (dtf.format(localDate));
    }

    public String getInDate() {
        return inDate;
    }

    public String getFile() {
        System.out.println("rental file : "+ file);
        return file;
    }
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

}
