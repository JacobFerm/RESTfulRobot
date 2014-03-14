package com.jayway.restfulrobot.infra;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

import com.jayway.restfulrobot.domain.Robot;
import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.rest.RobotController;

public class RobotList extends ResourceSupport {

	private static Map<Integer, Robot> robots = new HashMap<Integer, Robot>();
	private static int nextRobotId = 1;

	public ArrayList<Map<String, Object>> getRobots() {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Robot r : robots.values()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", r.getRobotId());
			map.put("name", r.getName());
			map.put("links",
					linkTo(
							methodOn(RobotController.class).getRobot(
									r.getRobotId())).withRel(
							"Robot " + r.getRobotId()));
			list.add(map);
		}
		return list;

	}

	public void addRobot(String name, Room room) {
		robots.put(nextRobotId, new Robot(nextRobotId, name, room));
		nextRobotId++;
	}

	public Robot getRobot(int id) {
		return robots.get(id);
	}

}
