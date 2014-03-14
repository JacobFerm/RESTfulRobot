package com.jayway.restfulrobot.rest.resource;

import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.rest.RobotController;
import com.jayway.restfulrobot.rest.RoomController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RoomResource extends ResourceSupport {

    private int roomId;
    private int width;
    private int height;

    public RoomResource(Room room) {
        roomId = room.getRoomId();
        width = room.getWidth();
        height = room.getHeight();

        this.add(linkTo(methodOn(RobotController.class).root()).withRel("Home"));
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
