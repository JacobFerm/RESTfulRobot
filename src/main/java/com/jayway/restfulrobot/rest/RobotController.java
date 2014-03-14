package com.jayway.restfulrobot.rest;

import com.jayway.restfulrobot.domain.Robot;
import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.infra.RobotRepository;
import com.jayway.restfulrobot.infra.RoomRepository;
import com.jayway.restfulrobot.rest.resource.RobotListResource;
import com.jayway.restfulrobot.rest.resource.RobotResource;
import com.jayway.restfulrobot.rest.resource.RootResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Controller
public class RobotController {

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping("/")
    @ResponseBody
    public HttpEntity<RootResource> root() {

        RootResource root = new RootResource();

        return new ResponseEntity<RootResource>(root, HttpStatus.OK);
    }

    @RequestMapping("/robots")
    @ResponseBody
    public HttpEntity<RobotListResource> getRobots() {

        Collection<Robot> robots = robotRepository.getRobots();
        RobotListResource robotListResource = new RobotListResource(robots);

        return new ResponseEntity<RobotListResource>(robotListResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/robots/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public HttpEntity<RobotListResource> addRobot(@RequestBody Map<String, Object> data) {

        if (!data.containsKey("roomId") || !data.containsKey("name")) {
            return new ResponseEntity<RobotListResource>(HttpStatus.BAD_REQUEST);
        }

        Room room = roomRepository.getRoom((Integer) data.get("roomId"));

        if (room == null) {
            return new ResponseEntity<RobotListResource>(HttpStatus.BAD_REQUEST);
        }

        robotRepository.addRobot((String) data.get("name"), room);

        return getRobots();
    }

    @RequestMapping("/robots/{id}")
    @ResponseBody
    public HttpEntity<RobotResource> getRobot(@PathVariable int id) {

        Robot robot = robotRepository.getRobot(id);

        if (robot == null) {
            return new ResponseEntity<RobotResource>(HttpStatus.BAD_REQUEST);
        }

        RobotResource robotResource = new RobotResource(robot);

        return new ResponseEntity<RobotResource>(robotResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/robots/{id}/turnleft", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<RobotResource> turnLeft(@PathVariable int id) {

        Robot robot = robotRepository.getRobot(id);
        robot.turnLeft();

        return getRobot(id);
    }

    @RequestMapping(value = "/robots/{id}/turnright", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<RobotResource> turnRight(@PathVariable int id) {

        Robot robot = robotRepository.getRobot(id);
        robot.turnRight();

        return getRobot(id);
    }

    @RequestMapping(value = "/robots/{id}/move", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<RobotResource> move(@PathVariable int id) {

        Robot robot = robotRepository.getRobot(id);
        robot.move();

        return getRobot(id);
    }

}
