package com.jayway.restfulrobot.infra.rest;

import com.jayway.restfulrobot.infra.rest.resource.RoomListResource;
import com.jayway.restfulrobot.infra.rest.resource.RoomResource;

import com.jayway.restfulrobot.domain.RoomRepository;
import com.jayway.restfulrobot.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping("/rooms")
    @ResponseBody
    public HttpEntity<RoomListResource> getRooms() {

        Collection<Room> rooms = roomRepository.getRooms();
        RoomListResource roomListResource = new RoomListResource(rooms);

        return new ResponseEntity<RoomListResource>(roomListResource, HttpStatus.OK);

    }

    @RequestMapping(value = "/rooms/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public HttpEntity<RoomListResource> addRoom(@RequestBody Map<String, Integer> data) {

        if (!data.containsKey("height") || !data.containsKey("width")) {
            return new ResponseEntity<RoomListResource>(HttpStatus.BAD_REQUEST);
        }

        roomRepository.addRoom(data.get("width"), data.get("height"));

        return getRooms();
    }

    @RequestMapping("/rooms/{id}")
    @ResponseBody
    public HttpEntity<RoomResource> getRoom(@PathVariable int id) {

        Room room = roomRepository.getRoom(id);
        RoomResource roomResource = new RoomResource(room);

        if (room == null) {
            return new ResponseEntity<RoomResource>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<RoomResource>(roomResource, HttpStatus.OK);
    }

}
