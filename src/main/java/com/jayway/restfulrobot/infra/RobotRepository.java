package com.jayway.restfulrobot.infra;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jayway.restfulrobot.domain.Robot;
import com.jayway.restfulrobot.domain.Room;

@Repository
public class RobotRepository {

	private static Map<Integer, Robot> robots = new HashMap<Integer, Robot>();
	private static int nextRobotId = 1;

	public Collection<Robot> getRobots() {
		return robots.values();
	}

	public void addRobot(String name, Room room) {
		robots.put(nextRobotId, new Robot(nextRobotId, name, room));
		nextRobotId++;
	}

	public Robot getRobot(int id) {
		return robots.get(id);
	}

}
