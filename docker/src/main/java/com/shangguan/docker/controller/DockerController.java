package com.shangguan.docker.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequestMapping("/docker")
public class DockerController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello Docker";
    }
}
