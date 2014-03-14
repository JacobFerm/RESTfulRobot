package com.jayway.restfulrobot.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.jayway.restfulrobot.rest.RobotController;
import com.jayway.restfulrobot.rest.RoomController;

public class RootResource extends ResourceSupport {

	public RootResource() {
		this.add(linkTo(methodOn(RobotController.class).root()).withSelfRel());
		this.add(linkTo(methodOn(RobotController.class).getRobots()).withRel(
				"List Robots"));
		this.add(linkTo(methodOn(RoomController.class).getRooms()).withRel(
				"List Rooms"));
	}

}
