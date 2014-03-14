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

import com.jayway.restfulrobot.domain.Robot;
import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.infra.RobotList;
import com.jayway.restfulrobot.infra.RobotRoot;
import com.jayway.restfulrobot.infra.RoomList;

@Controller
public class RobotController {

	@RequestMapping("/")
	@ResponseBody
	public HttpEntity<RobotRoot> root() {

		RobotRoot root = new RobotRoot();
		root.add(linkTo(methodOn(RobotController.class).root()).withSelfRel());
		root.add(linkTo(methodOn(RobotController.class).getRobots()).withRel(
				"List Robots"));
		root.add(linkTo(methodOn(RoomController.class).getRooms()).withRel(
				"List Rooms"));

		return new ResponseEntity<RobotRoot>(root, HttpStatus.OK);
	}

	@RequestMapping("/robots")
	@ResponseBody
	public HttpEntity<RobotList> getRobots() {
		RobotList robotList = new RobotList();

		robotList.add(linkTo(methodOn(RobotController.class).root()).withRel(
				"Home"));
		robotList.add(linkTo(methodOn(RobotController.class).getRobots())
				.withSelfRel());

		robotList.add(linkTo(methodOn(RobotController.class).addRobot(null))
				.withRel("Add Robot"));

		return new ResponseEntity<RobotList>(robotList, HttpStatus.OK);
	}

	@RequestMapping(value = "/robots/add", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HttpEntity<RobotList> addRobot(@RequestBody Map<String, Object> data) {

		if (!data.containsKey("roomId") || !data.containsKey("name")) {
			return new ResponseEntity<RobotList>(HttpStatus.BAD_REQUEST);
		}

		RoomList roomList = new RoomList();
		Room room = roomList.getRoom((Integer) data.get("roomId"));

		if (room == null) {
			return new ResponseEntity<RobotList>(HttpStatus.BAD_REQUEST);
		}
		RobotList robotList = new RobotList();
		robotList.addRobot((String) data.get("name"), room);

		return getRobots();
	}

	@RequestMapping("/robots/{id}")
	@ResponseBody
	public HttpEntity<Robot> getRobot(@PathVariable int id) {

		RobotList robotList = new RobotList();
		Robot robot = robotList.getRobot(id);

		if (robot == null) {
			return new ResponseEntity<Robot>(HttpStatus.BAD_REQUEST);
		}

		setLinks(robot);

		return new ResponseEntity<Robot>(robot, HttpStatus.OK);
	}

	@RequestMapping(value = "/robots/{id}/turnleft", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<Robot> turnLeft(@PathVariable int id) {

		RobotList robotList = new RobotList();
		Robot robot = robotList.getRobot(id);
		robot.turnLeft();

		return getRobot(id);
	}

	@RequestMapping(value = "/robots/{id}/turnright", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<Robot> turnRight(@PathVariable int id) {

		RobotList robotList = new RobotList();
		Robot robot = robotList.getRobot(id);
		robot.turnRight();

		return getRobot(id);
	}

	@RequestMapping(value = "/robots/{id}/move", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<Robot> move(@PathVariable int id) {

		RobotList robotList = new RobotList();
		Robot robot = robotList.getRobot(id);
		robot.move();

		return getRobot(id);
	}

	public void setLinks(Robot robot) {
		robot.removeLinks();
		robot.add(linkTo(methodOn(RobotController.class).root())
				.withRel("Home"));

		robot.add(linkTo(
				methodOn(RobotController.class).getRobot(robot.getRobotId()))
				.withSelfRel());

		robot.add(linkTo(
				methodOn(RobotController.class).turnLeft(robot.getRobotId()))
				.withRel("Turn Left"));
		robot.add(linkTo(
				methodOn(RobotController.class).turnRight(robot.getRobotId()))
				.withRel("Turn Right"));
		if (robot.canMove()) {
			robot.add(linkTo(
					methodOn(RobotController.class).move(robot.getRobotId()))
					.withRel("Move Robot Forward"));
		}
	}

}
