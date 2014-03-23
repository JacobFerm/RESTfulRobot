package com.jayway.restfulrobot.infra.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.infra.rest.RobotController;
import com.jayway.restfulrobot.infra.rest.RoomController;

public class RoomResource extends ResourceSupport {

    private int roomId;
    private int width;
    private int height;

    public RoomResource(Room room) {
        roomId = room.getRoomId();
        width = room.getWidth();
        height = room.getHeight();

        this.add(linkTo(methodOn(RobotController.class).root()).withRel("home"));
        this.add(linkTo(methodOn(RoomController.class).getRoom(room.getRoomId())).withSelfRel());
    }

    public int getRoomId() {
        return roomId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
