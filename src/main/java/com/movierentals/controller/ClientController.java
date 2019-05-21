package com.movierentals.controller;

import com.movierentals.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientController implements CommonController{

    @Autowired
    ClientService client;

    @Override
    @RequestMapping(value = "/create/{name}",method = RequestMethod.POST)
    public boolean create(@PathVariable String name){
        return client.create(name.toLowerCase());
    }

    @RequestMapping(value = "/delete/{name}",method = RequestMethod.DELETE)
    public boolean update(@PathVariable String name){
        return client.update(name.toLowerCase(), true);
    }

    @Override
    @RequestMapping(value = "/find/{name}",method = RequestMethod.GET)
    public List get(@PathVariable String name){
        return client.get(name.toLowerCase());
    }

    @Override
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List getAll(){
        return client.getAll(false);
    }
}