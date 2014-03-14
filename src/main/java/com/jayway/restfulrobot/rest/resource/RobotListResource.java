package com.jayway.restfulrobot.rest.resource;

import com.jayway.restfulrobot.domain.Robot;
import com.jayway.restfulrobot.rest.RobotController;
import org.springframework.hateoas.ResourceSupport;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RobotListResource extends ResourceSupport {

    ArrayList<Map<String, Object>> robots;

    public RobotListResource(Collection<Robot> robotList) {
        robots = new ArrayList<Map<String, Object>>();
        for (Robot r : robotList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", r.getRobotId());
            map.put("name", r.getName());
            map.put("links", linkTo(methodOn(RobotController.class).getRobot(r.getRobotId())).withRel("Robot " + r.getRobotId()));
            robots.add(map);
        }

        this.add(linkTo(methodOn(RobotController.class).root()).withRel("Home"));
        this.add(linkTo(methodOn(RobotController.class).getRobots()).withSelfRel());
        this.add(linkTo(methodOn(RobotController.class).addRobot(null)).withRel("Add Robot"));
    }

    public ArrayList<Map<String, Object>> getRobots() {
        return robots;

    }

}
