package com.movierentals.controller;

import com.movierentals.model.RentalInfo;
import com.movierentals.service.MoviesService;
import com.movierentals.service.RentalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MoviesController implements  CommonController{

    @Autowired
    MoviesService movies;

    @Autowired
    RentalInfoService rentalInfo;

    @RequestMapping(value = "/create/{name}",method = RequestMethod.POST)
    public boolean create(@PathVariable String name) {
        return movies.create(name.toLowerCase());
    }

    @RequestMapping(value = "/rent",method = RequestMethod.PUT)
    public List update(@RequestBody RentalInfo info) {
        List response;
        try {
            rentalInfo.create(info);
            response = new ArrayList();
            response.add(movies.update(info.getMovie().toLowerCase(),true)+"");
            return response;
        } catch (Exception e) {
            response = new ArrayList();
            response.add(e.getMessage());
            return response;
        }
    }

    @RequestMapping(value = "/return",method = RequestMethod.PUT)
    public boolean returnMovie(@RequestBody RentalInfo info){
        try{
            rentalInfo.update(info);
            String movie = rentalInfo.getMovie(info.getId());
            return movies.update(movie,false);
        }catch (Exception e){
            return false;
        }
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List getAll() {
        return movies.getAll(false);
    }

    @RequestMapping(value = "/rented",method = RequestMethod.GET)
    public List get(String name) {
        return movies.getAll(true);
    }

    @RequestMapping(value = "/history/{movie}",method = RequestMethod.GET)
    public List getRented(@PathVariable String movie){
        return rentalInfo.getAll(movie.toLowerCase());
    }
}

