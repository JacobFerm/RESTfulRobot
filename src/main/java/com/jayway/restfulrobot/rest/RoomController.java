package com.jayway.restfulrobot.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.infra.RoomList;

@Controller
public class RoomController {

	@RequestMapping("/rooms")
	@ResponseBody
	public HttpEntity<RoomList> getRooms() {

		RoomList roomList = new RoomList();
		roomList.add(linkTo(methodOn(RobotController.class).root()).withRel(
				"Home"));
		roomList.add(linkTo(methodOn(RoomController.class).getRooms())
				.withSelfRel());

		roomList.add(linkTo(methodOn(RoomController.class).addRoom(null))
				.withRel("Add Room"));
		return new ResponseEntity<RoomList>(roomList, HttpStatus.OK);

	}

	@RequestMapping(value = "/rooms/add", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HttpEntity<RoomList> addRoom(@RequestBody Map<String, Integer> data) {

		if (!data.containsKey("height") || !data.containsKey("width")) {
			return new ResponseEntity<RoomList>(HttpStatus.BAD_REQUEST);
		}

		RoomList roomList = new RoomList();
		roomList.addRoom(data.get("width"), data.get("height"));

		return getRooms();
	}

	@RequestMapping("/rooms/{id}")
	@ResponseBody
	public HttpEntity<Room> getRoom(@PathVariable int id) {

		RoomList roomList = new RoomList();
		Room room = roomList.getRoom(id);

		if (room == null) {
			return new ResponseEntity<Room>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Room>(room, HttpStatus.OK);
	}

}
