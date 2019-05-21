package com.movierentals.service;

import com.movierentals.model.Client;
import com.movierentals.model.Movie;
import com.movierentals.model.RentalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalInfoService {

    @Autowired
    RentalInfo rentalInfo;
    @Autowired
    Client client;
    @Autowired
    Movie movie;


    public FileService getService() {
        return new FileService();
    }

    public List get(String name) {
        return getService().get(new RentalInfo().getFile(),name);
    }

    public boolean update(RentalInfo info) throws Exception {
        if(!getService().updateRentalInfo(info)){
            throw new Exception("error in update");
        }else {
            return true;
        }
    }

    public boolean create(RentalInfo rentalInfo) throws Exception {
        String clientId = getService().getId(client.getFile(), rentalInfo.getClient());
        String movieId = getService().getId(movie.getFile(), rentalInfo.getMovie());
        List info = getService().get(rentalInfo.getFile(),clientId);
        if (info.size()<3) {
            if (clientId == null) {
                throw new Exception("Create Client first");
            } if (clientId != null) {
                getService().checkClientHistroy(rentalInfo);
            } if (movieId == null) {
                throw new Exception("Create Movie first");
            } if (movieId != null) {
                getService().checkMovieHistroy(new Movie().getFile(),rentalInfo.getMovie());
            }
            getService().createRentalInfo(rentalInfo);
            return true;
        } else {
            throw new Exception("Client is unable to rent a movie");
        }
    }

    public List getAll(String movie) {
        return getService().getMovieHistory(rentalInfo.getFile(),movie);
    }
    public List getAll(){
        return getService().getAll(rentalInfo.getFile());
    }

    public String getMovie(int id) {
        return getService().getMovieName(id);
    }
}
