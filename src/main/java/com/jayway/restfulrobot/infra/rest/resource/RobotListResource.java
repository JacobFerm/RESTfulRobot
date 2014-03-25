package com.jayway.restfulrobot.infra.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.*;

import org.springframework.hateoas.ResourceSupport;

import com.jayway.restfulrobot.domain.Robot;
import com.jayway.restfulrobot.infra.rest.RobotController;

public class RobotListResource extends ResourceSupport {

    ArrayList<Map<String, Object>> robots;

    public RobotListResource(Collection<Robot> robotList) {
        robots = new ArrayList<Map<String, Object>>();
        for (Robot r : robotList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", r.getRobotId());
            map.put("name", r.getName());
            map.put("_links", linkTo(methodOn(RobotController.class).getRobot(r.getRobotId())).withRel("robot-" + r.getRobotId()));
            robots.add(map);
        }

        this.add(linkTo(methodOn(RobotController.class).root()).withRel("home"));
        this.add(linkTo(methodOn(RobotController.class).getRobots()).withSelfRel());
        this.add(linkTo(methodOn(RobotController.class).addRobot(null)).withRel("add-robot"));
    }

    public ArrayList<Map<String, Object>> getRobots() {
        return robots;

    }

}
