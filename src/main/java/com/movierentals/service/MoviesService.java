package com.movierentals.service;

import com.movierentals.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesService {

    @Autowired
    Movie movie;

    FileService service = new FileService();

    public boolean create(String name) {
        movie.setName(name);
        return service.create(movie.getFile(),movie.getName());
    }

    public boolean update(String name, boolean flag) {
        movie.setName(name);
        movie.setRented(flag);
        return service.update(movie.getFile(),movie.getName(),movie.isRented());
    }

    public List get(String name) {
        movie.setName(name);
        return service.get(movie.getFile(),movie.getName());
    }

    public List getAll(boolean flag) {
        if(flag){
            return service.getAll(movie.getFile(),true);
        }else
            return service.getAll(movie.getFile(),false);
    }
}
