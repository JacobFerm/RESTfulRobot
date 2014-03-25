package com.jayway.restfulrobot.infra.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.jayway.restfulrobot.domain.*;
import com.jayway.restfulrobot.infra.rest.resource.RootResource;

@Controller
public class RobotController {

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping("/")
    @ResponseBody
    public HttpEntity<RootResource> root() {
        RootResource root = new RootResource();
        return new ResponseEntity<RootResource>(root, HttpStatus.OK);
    }

}
