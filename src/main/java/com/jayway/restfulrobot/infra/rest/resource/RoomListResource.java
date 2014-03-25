package com.jayway.restfulrobot.infra.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.*;

import org.springframework.hateoas.ResourceSupport;

import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.infra.rest.RobotController;
import com.jayway.restfulrobot.infra.rest.RoomController;

public class RoomListResource extends ResourceSupport {

    ArrayList<Map<String, Object>> rooms;

    public RoomListResource(Collection<Room> roomList) {
        rooms = new ArrayList<Map<String, Object>>();
        for (Room r : roomList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", r.getRoomId());
            map.put("_links", linkTo(methodOn(RoomController.class).getRoom(r.getRoomId())).withRel("room-" + r.getRoomId()));
            rooms.add(map);
        }

        this.add(linkTo(methodOn(RobotController.class).root()).withRel("home"));
        this.add(linkTo(methodOn(RoomController.class).getRooms()).withSelfRel());
        this.add(linkTo(methodOn(RoomController.class).addRoom(null)).withRel("add-room"));
    }

    public ArrayList<Map<String, Object>> getRooms() {
        return rooms;
    }

}
