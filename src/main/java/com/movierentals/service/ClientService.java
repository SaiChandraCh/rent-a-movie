package com.movierentals.service;

import com.movierentals.model.Client;
import com.movierentals.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService{

    @Autowired
    Client client;

    public FileService getService() {
        return new FileService();
    }

    public boolean create(String name) {
        client.setName(name);
        return getService().create(client.getFile(),client.getName());
    }

    public boolean update(String name, boolean flag) {
        client.setName(name);
        return getService().update(client.getFile(),client.getName(),flag);
    }

    public List get(String name) {
        client.setName(name);
        return getService().getClient(client.getFile(),client.getName());
    }

    public List getAll(boolean flag) {
        if(flag){
            return getService().getAll(client.getFile(), true);
        }else
            return getService().getAll(client.getFile(), false);
    }

}
