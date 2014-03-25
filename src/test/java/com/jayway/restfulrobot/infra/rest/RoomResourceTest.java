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
import com.jayway.restfulrobot.domain.RoomRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class RoomResourceTest {

    // @formatter:off
    
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
        rooms.reset();
    }
    
    @Test
    public void canGetRoomListResource() {
        when().
                get("/rooms").
        then().
                statusCode(200).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/rooms")).
                body(withArgs("add-room"), endsWith("/rooms/add"));
    }
    
    @Test
    public void canListRooms() {
         // given
                rooms.addRoom(1, 1);
                rooms.addRoom(2, 3);
        when().
                get("/rooms").
        then().
                statusCode(200).
                body("rooms.size()", equalTo(2)).
                rootPath("rooms[1]").
                body("id", equalTo(2)).
                body("_links.href", endsWith("/rooms/2"));
    }
    
    @Test
    public void canGetRoom() {
        // given
                rooms.addRoom(2, 3);
        when().
                get("/rooms/1").
        then().
                statusCode(200).
                body("roomId", equalTo(1)).
                body("width", equalTo(2)).
                body("height", equalTo(3)).
                root("_links.%s.href").
                body(withArgs("home"), notNullValue()).
                body(withArgs("self"), endsWith("/rooms/1"));
    }
    
    @Test
    public void canCreateRoom() {
        // given
                HashMap<String, Object> jsonParameters = new HashMap<String, Object>();
                jsonParameters.put("width", 2);
                jsonParameters.put("height", 3);
                given().
                contentType("application/json").
                body(jsonParameters).
        when().
                post("/rooms/add").
        then().
                statusCode(201).
                body("rooms.size()", equalTo(1)).
                rootPath("rooms[0]").
                body("id", equalTo(1)).
                body("_links.href", endsWith("/rooms/1"));
    }

    // @formatter:on

}