package robot;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class Robot extends ResourceSupport {

	public static final int NORTH = 0;
	public static final int WEST = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;

	private int robotId;
	private int xPos;
	private int yPos;
	private Room room;
	private int dir;

	public Robot(int id, Room room) {
		this.robotId = id;
		xPos = 0;
		yPos = 0;
		this.room = room;
		dir = NORTH;
		setLinks();
	}

	public int getRobotId() {
		return robotId;
	}

	public Link getRoom() {
		return linkTo(methodOn(RobotController.class).getRoom(room.getRoomId()))
				.withSelfRel();
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public int getDir() {
		return dir;
	}

	public void turnLeft() {
		dir = (dir + 1) % 4;
		setLinks();
	}

	public void turnRight() {
		dir = (dir + 3) % 4;
		setLinks();
	}

	public void move() {
		int newX = 0;
		int newY = 0;
		switch (dir) {

		case NORTH:
			newX = xPos;
			newY = yPos + 1;
			break;
		case WEST:
			newX = xPos - 1;
			newY = yPos;
			break;
		case SOUTH:
			newX = xPos;
			newY = yPos - 1;
			break;
		case EAST:
			newX = xPos + 1;
			newY = yPos;
			break;
		}

		if (room.contains(newX, newY)) {
			xPos = newX;
			yPos = newY;
		}

		setLinks();
	}

	private void setLinks() {
		int newX = 0;
		int newY = 0;

		switch (dir) {

		case NORTH:
			newX = xPos;
			newY = yPos + 1;
			break;
		case WEST:
			newX = xPos - 1;
			newY = yPos;
			break;
		case SOUTH:
			newX = xPos;
			newY = yPos - 1;
			break;
		case EAST:
			newX = xPos + 1;
			newY = yPos;
			break;
		}

		this.removeLinks();
		this.add(linkTo(methodOn(RobotController.class).turnLeft(robotId))
				.withRel("Turn Left"));
		this.add(linkTo(methodOn(RobotController.class).turnRight(robotId))
				.withRel("Turn Right"));
		if (room.contains(newX, newY)) {
			this.add(linkTo(methodOn(RobotController.class).move(robotId))
					.withRel("Move Robot Forward"));
		}
	}

}
