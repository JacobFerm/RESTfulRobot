package robot;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;
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

@Controller
public class RobotController {

	@RequestMapping("/")
	@ResponseBody
	public HttpEntity<RobotRoot> root() {

		RobotRoot root = new RobotRoot();
		root.add(linkTo(methodOn(RobotController.class).root()).withSelfRel());
		root.add(linkTo(methodOn(RobotController.class).getRobots())
				.withSelfRel());
		root.add(linkTo(methodOn(RobotController.class).getRooms())
				.withSelfRel());

		return new ResponseEntity<RobotRoot>(root, HttpStatus.OK);
	}

	@RequestMapping("/robots")
	@ResponseBody
	public HttpEntity<RobotList> getRobots() {

		RobotList list = new RobotList();
		list.add(linkTo(methodOn(RobotController.class).getRobots())
				.withSelfRel());
		return new ResponseEntity<RobotList>(list, HttpStatus.OK);

	}

	@RequestMapping(value = "/robots/add", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HttpEntity<RobotList> addRobot(@RequestBody Map<String, Integer> data) {

		if (!data.containsKey("roomId")) {
			return new ResponseEntity<RobotList>(HttpStatus.BAD_REQUEST);
		}

		RoomList roomList = new RoomList();
		Room room = roomList.getRoom(data.get("roomId"));

		if (room == null) {
			return new ResponseEntity<RobotList>(HttpStatus.BAD_REQUEST);
		}
		RobotList robotList = new RobotList();
		robotList.addRobot(room);

		return getRobots();
	}

	@RequestMapping("/robots/{id}")
	@ResponseBody
	public HttpEntity<Robot> getRobot(@PathVariable int id) {

		RobotList list = new RobotList();
		Robot robot = list.getRobot(id);

		if (robot == null) {
			return new ResponseEntity<Robot>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Robot>(robot, HttpStatus.OK);
	}

	@RequestMapping("/robots/{id}/turnleft")
	@ResponseBody
	public HttpEntity<Robot> turnLeft(@PathVariable int id) {

		RobotList list = new RobotList();
		Robot robot = list.getRobot(id);
		robot.turnLeft();

		return getRobot(id);
	}

	@RequestMapping("/robots/{id}/turnright")
	@ResponseBody
	public HttpEntity<Robot> turnRight(@PathVariable int id) {

		RobotList list = new RobotList();
		Robot robot = list.getRobot(id);
		robot.turnRight();

		return getRobot(id);
	}

	@RequestMapping("/robots/{id}/move")
	@ResponseBody
	public HttpEntity<Robot> move(@PathVariable int id) {

		RobotList list = new RobotList();
		Robot robot = list.getRobot(id);
		robot.move();

		return getRobot(id);
	}

	@RequestMapping("/rooms")
	@ResponseBody
	public HttpEntity<RoomList> getRooms() {

		RoomList list = new RoomList();
		list.add(linkTo(methodOn(RobotController.class).getRooms())
				.withSelfRel());

		HashMap<String, Integer> defaultRoom = new HashMap<String, Integer>();
		defaultRoom.put("width", 5);
		defaultRoom.put("height", 5);
		list.add(linkTo(methodOn(RobotController.class).addRoom(defaultRoom))
				.withSelfRel());

		return new ResponseEntity<RoomList>(list, HttpStatus.OK);

	}

	@RequestMapping(value = "rooms/add", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HttpEntity<RoomList> addRoom(@RequestBody Map<String, Integer> data) {

		if (!data.containsKey("height") || !data.containsKey("width")) {
			return new ResponseEntity<RoomList>(HttpStatus.BAD_REQUEST);
		}

		RoomList list = new RoomList();
		list.addRoom(data.get("width"), data.get("height"));

		return getRooms();
	}

	@RequestMapping("/rooms/{id}")
	@ResponseBody
	public HttpEntity<Room> getRoom(@PathVariable int id) {

		RoomList list = new RoomList();
		Room room = list.getRoom(id);

		if (room == null) {
			return new ResponseEntity<Room>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Room>(room, HttpStatus.OK);
	}

}
