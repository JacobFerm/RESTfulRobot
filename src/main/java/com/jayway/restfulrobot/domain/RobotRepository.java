package com.jayway.restfulrobot.domain;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RobotRepository {

    private Map<Integer, Robot> robots = new HashMap<Integer, Robot>();
    private int nextRobotId = 1;

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
