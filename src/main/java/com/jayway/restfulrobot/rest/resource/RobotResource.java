package com.jayway.restfulrobot.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.jayway.restfulrobot.domain.Robot;
import com.jayway.restfulrobot.rest.RobotController;
import com.jayway.restfulrobot.rest.RoomController;

public class RobotResource extends ResourceSupport {

	private int robotId;
	private String name;
	private int xPos;
	private int yPos;
	private int dir;
	private Link room;

	public RobotResource(Robot robot) {
		robotId = robot.getRobotId();
		name = robot.getName();
		xPos = robot.getXPos();
		yPos = robot.getYPos();
		dir = robot.getDir();

		room = linkTo(
				methodOn(RoomController.class).getRoom(
						robot.getRoom().getRoomId())).withRel(
				"Room " + robot.getRoom().getRoomId());

		this.add(linkTo(methodOn(RobotController.class).root()).withRel("Home"));

		this.add(linkTo(
				methodOn(RobotController.class).getRobot(robot.getRobotId()))
				.withSelfRel());

		this.add(linkTo(
				methodOn(RobotController.class).turnLeft(robot.getRobotId()))
				.withRel("Turn Left"));
		this.add(linkTo(
				methodOn(RobotController.class).turnRight(robot.getRobotId()))
				.withRel("Turn Right"));
		if (robot.canMove()) {
			this.add(linkTo(
					methodOn(RobotController.class).move(robot.getRobotId()))
					.withRel("Move Robot Forward"));
		}
	}

	public int getRobotId() {
		return robotId;
	}

	public String getName() {
		return name;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public Link getRoom() {
		return room;
	}

	public int getDir() {
		return dir;
	}

}
