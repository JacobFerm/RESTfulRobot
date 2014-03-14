package robot;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class RoomList extends ResourceSupport {

	private static Map<Integer, Room> rooms = new HashMap<Integer, Room>();
	private static int nextRoomId = 1;

	public Map<Integer, Link> getRooms() {

		Map<Integer, Link> linkMap = new HashMap<Integer, Link>();

		for (Integer i : rooms.keySet()) {
			linkMap.put(i, linkTo(methodOn(RobotController.class).getRoom(i))
					.withRel("Room " + i));
		}
		return linkMap;
	}

	public void addRoom(int width, int height) {
		rooms.put(nextRoomId, new Room(nextRoomId, width, height));
		nextRoomId++;
	}

	public Room getRoom(int roomId) {
		return rooms.get(roomId);
	}

}
