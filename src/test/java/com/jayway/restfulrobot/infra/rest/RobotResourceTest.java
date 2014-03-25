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
public class RobotResourceTest {

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
    public void resetRepository() {
        robots.reset();
        rooms.reset();
    }
    
    @Test
    public void canGetRobotListResource() {
        when().
                get("/robots").
        then().
                statusCode(200).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/robots")).
                body(withArgs("add-robot"), endsWith("/robots/add"));
    }
    
    @Test
    public void canListRobots() {
         // given
                robots.addRobot("robot1", new Room(1, 2, 2));
                robots.addRobot("robot2", new Room(1, 2, 2));
        when().
                get("/robots").
        then().
                statusCode(200).
                body("robots.size()", equalTo(2)).
                rootPath("robots[0]").
                body("id", equalTo(1)).
                body("name", equalTo("robot1")).
                body("_links.href", endsWith("/robots/1"));
    }
    
    @Test
    public void canGetRobot() {
        // given
                robots.addRobot("robot1", new Room(1, 2, 2));
        when().
                get("/robots/1").
        then().
                statusCode(200).
                body("robotId", equalTo(1)).
                body("name", equalTo("robot1")).
                body("xPos", equalTo(1)).
                body("yPos", equalTo(1)).
                body("dir", equalTo(0)).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/robots/1"));
    }
    
    @Test
    public void canCreateRobot() {
        // given
                HashMap<String, Object> jsonParameters = new HashMap<String, Object>();
                jsonParameters.put("roomId", 1);
                jsonParameters.put("name", "robot1");
                rooms.addRoom(2, 2);
                given().
                contentType("application/json").
                body(jsonParameters).
        when().
                post("/robots/add").
        then().
                statusCode(201).
                body("robots.size()", equalTo(1)).
                rootPath("robots[0]").
                body("id", equalTo(1)).
                body("name", equalTo("robot1")).
                body("_links.href", endsWith("/robots/1"));
    }

    // @formatter:on

}