package com.jayway.restfulrobot.infra.rest.resource;

import com.jayway.restfulrobot.infra.rest.RobotController;
import com.jayway.restfulrobot.infra.rest.RoomController;

import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RootResource extends ResourceSupport {

    public RootResource() {
        this.add(linkTo(methodOn(RobotController.class).root()).withSelfRel());
        this.add(linkTo(methodOn(RobotController.class).getRobots()).withRel("List Robots"));
        this.add(linkTo(methodOn(RoomController.class).getRooms()).withRel("List Rooms"));
    }

}
