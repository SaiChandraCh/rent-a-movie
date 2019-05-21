package com.movierentals.controller;


import com.movierentals.model.RentalInfo;
import com.movierentals.service.RentalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rental-info")
@CrossOrigin(origins = "http://localhost:3000")
public class RentalInfoController {

    @Autowired
    RentalInfoService rentalInfo;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List getAll() {
        return rentalInfo.getAll();
    }
}
