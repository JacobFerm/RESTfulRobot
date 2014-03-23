package com.jayway.restfulrobot.infra.rest.resource;

import com.jayway.restfulrobot.infra.rest.RobotController;
import com.jayway.restfulrobot.infra.rest.RoomController;

import com.jayway.restfulrobot.domain.Room;
import org.springframework.hateoas.ResourceSupport;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RoomListResource extends ResourceSupport {

    ArrayList<Map<String, Object>> rooms;

    public RoomListResource(Collection<Room> roomList) {
        rooms = new ArrayList<Map<String, Object>>();
        for (Room r : roomList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", r.getRoomId());
            map.put("links", linkTo(methodOn(RoomController.class).getRoom(r.getRoomId())).withRel("Room " + r.getRoomId()));
            rooms.add(map);
        }

        this.add(linkTo(methodOn(RobotController.class).root()).withRel("Home"));
        this.add(linkTo(methodOn(RoomController.class).getRooms()).withSelfRel());
        this.add(linkTo(methodOn(RoomController.class).addRoom(null)).withRel("Add Room"));
    }

    public ArrayList<Map<String, Object>> getRooms() {
        return rooms;
    }

}
