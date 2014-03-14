package com.jayway.restfulrobot.infra;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.rest.RobotController;
import com.jayway.restfulrobot.rest.RoomController;

public class RoomList extends ResourceSupport {

	private static Map<Integer, Room> rooms = new HashMap<Integer, Room>();
	private static int nextRoomId = 1;

	public Map<Integer, Link> getRooms() {

		Map<Integer, Link> linkMap = new HashMap<Integer, Link>();

		for (Integer i : rooms.keySet()) {
			linkMap.put(i, linkTo(methodOn(RoomController.class).getRoom(i))
					.withRel("Room " + i));
		}
		return linkMap;
	}

	public void addRoom(int width, int height) {
		Room room = new Room(nextRoomId, width, height);
		room.add(linkTo(methodOn(RobotController.class).root()).withRel("Home"));
		room.add(linkTo(methodOn(RoomController.class).getRoom(nextRoomId))
				.withSelfRel());
		rooms.put(nextRoomId, room);
		nextRoomId++;
	}

	public Room getRoom(int roomId) {
		return rooms.get(roomId);
	}

}
