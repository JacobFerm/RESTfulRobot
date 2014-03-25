package com.jayway.restfulrobot.infra.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.jayway.restfulrobot.infra.rest.RobotController;

public class RootResource extends ResourceSupport {

    public RootResource() {
        this.add(linkTo(methodOn(RobotController.class).root()).withSelfRel());
        // this.add(linkTo(methodOn(RobotController.class).getRobots()).withRel("list-robots"));
        // this.add(linkTo(methodOn(RoomController.class).getRooms()).withRel("list-rooms"));
    }

}
