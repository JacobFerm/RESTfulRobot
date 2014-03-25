package com.jayway.restfulrobot.infra.rest;

import static com.jayway.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restfulrobot.boot.Application;
import com.jayway.restfulrobot.domain.RobotRepository;
import com.jayway.restfulrobot.domain.Room;
import com.jayway.restfulrobot.domain.RoomRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class RobotMovementTest {

    // @formatter:off
    
    @Autowired
    RobotRepository robots;
    
    @Autowired
    RoomRepository rooms;
    
    @Autowired
    WebApplicationContext wac;

    @Before public void
    given_rest_assured_is_configured_with_the_web_application_context() {
        RestAssuredMockMvc.webAppContextSetup(wac);
        RestAssuredMockMvc.basePath = "/";
    }
    
    @Before
    public void reset() {
        robots.reset();
        rooms.reset();
    }
    
    @Test
    public void getRobotReturnsMovementLinks() {
        // given
                rooms.addRoom(5, 5);
                robots.addRobot("robot1", rooms.getRoom(1));
        when().
                get("/robots/1").
        then().
                statusCode(200).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/robots/1")).
                body(withArgs("turn-left"), endsWith("/robots/1/turnleft")).
                body(withArgs("turn-right"), endsWith("/robots/1/turnright")).
                body(withArgs("move-robot-forward"), endsWith("/robots/1/move"));
    }
    
    @Test
    public void robotCanTurnLeft() {
        // given
                rooms.addRoom(5, 5);
                robots.addRobot("robot1", rooms.getRoom(1));
        when().
                post("/robots/1/turnleft").
        then().
                statusCode(200).
                body("robotId", equalTo(1)).
                body("name", equalTo("robot1")).
                body("xPos", equalTo(1)).
                body("yPos", equalTo(1)).
                body("dir", equalTo(1)).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/robots/1"));
    }
    

    @Test
    public void robotCanTurnRight() {
        // given
                rooms.addRoom(5, 5);
                robots.addRobot("robot1", rooms.getRoom(1));
        when().
                post("/robots/1/turnright").
        then().
                statusCode(200).
                body("robotId", equalTo(1)).
                body("name", equalTo("robot1")).
                body("xPos", equalTo(1)).
                body("yPos", equalTo(1)).
                body("dir", equalTo(3)).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/robots/1"));
    }
    
    @Test
    public void robotCanMoveNorth() {
        // given
                rooms.addRoom(5, 5);
                robots.addRobot("robot1", rooms.getRoom(1));
        when().
                post("/robots/1/move").
        then().
                statusCode(200).
                body("robotId", equalTo(1)).
                body("name", equalTo("robot1")).
                body("xPos", equalTo(1)).
                body("yPos", equalTo(2)).
                body("dir", equalTo(0)).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/robots/1"));
    }
    
    @Test
    public void robotCanMoveEast() {
        // given
                rooms.addRoom(5, 5);
                robots.addRobot("robot1", rooms.getRoom(1));
                post("/robots/1/turnright");
        when().
                post("/robots/1/move").
        then().
                statusCode(200).
                body("robotId", equalTo(1)).
                body("name", equalTo("robot1")).
                body("xPos", equalTo(2)).
                body("yPos", equalTo(1)).
                body("dir", equalTo(3)).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/robots/1"));
    }
    
    @Test
    public void robotCanNotMoveIntoWall() {
        // given
                rooms.addRoom(2, 2);
                robots.addRobot("robot1", rooms.getRoom(1));
        when().
                post("/robots/1/move").
        then().
                statusCode(400);
    }
    
    @Test
    public void noMoveLinkReturnedWhenFacingWall() {
        // given
                rooms.addRoom(3, 3);
                robots.addRobot("robot1", rooms.getRoom(1));
        when().
                post("/robots/1/move").
        then().
                statusCode(200).
                body("_links.move-robot-forward", nullValue()).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/robots/1")).
                body(withArgs("turn-left"), endsWith("/robots/1/turnleft")).
                body(withArgs("turn-right"), endsWith("/robots/1/turnright"));
    }
    
    // @formatter:on

}
