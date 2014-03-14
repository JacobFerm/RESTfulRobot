package robot;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class RobotList extends ResourceSupport {

	private static Map<Integer, Robot> robots = new HashMap<Integer, Robot>();
	private static int nextRobotId = 1;

	public Map<Integer, Link> getRobots() {
		Map<Integer, Link> linkMap = new HashMap<Integer, Link>();

		for (Integer i : robots.keySet()) {
			linkMap.put(i, linkTo(methodOn(RobotController.class).getRobot(i))
					.withRel("Robot " + i));
		}
		return linkMap;
	}

	public void addRobot(Room room) {
		robots.put(nextRobotId, new Robot(nextRobotId, room));
		nextRobotId++;
	}

	public Robot getRobot(int id) {
		return robots.get(id);
	}

}
