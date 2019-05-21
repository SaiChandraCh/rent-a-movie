package com.movierentals.controller;

import java.util.List;

public interface CommonController {

    boolean create(String name);
    List get(String name);
    List getAll();
}
