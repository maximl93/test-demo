package com.example.demo.controller;

import com.example.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class Controller {

    @Autowired
    Utils utils;

    @GetMapping("")
    public int find(@RequestParam("filePath") String filePath, @RequestParam("nthMin") int nthMin) {
        return utils.findNthMinimum(filePath, nthMin);
    }
}
