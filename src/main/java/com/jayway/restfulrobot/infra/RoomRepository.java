package com.jayway.restfulrobot.infra;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jayway.restfulrobot.domain.Room;

@Repository
public class RoomRepository {

	private static Map<Integer, Room> rooms = new HashMap<Integer, Room>();
	private static int nextRoomId = 1;

	public Collection<Room> getRooms() {
		return rooms.values();
	}

	public void addRoom(int width, int height) {
		rooms.put(nextRoomId, new Room(nextRoomId, width, height));
		nextRoomId++;
	}

	public Room getRoom(int roomId) {
		return rooms.get(roomId);
	}

}
