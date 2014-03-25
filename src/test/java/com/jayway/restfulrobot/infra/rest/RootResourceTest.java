package com.jayway.restfulrobot.infra.rest;

import static com.jayway.restassured.RestAssured.withArgs;
//import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class RootResourceTest {

    // @formatter:off
    
    @Autowired
    WebApplicationContext wac;

    @Before public void
    given_rest_assured_is_configured_with_the_web_application_context() {
        RestAssuredMockMvc.webAppContextSetup(wac);
        RestAssuredMockMvc.basePath = "/";
    }
    
    @Test
    public void canGetRootResource() {
        when().
                get("/").
        then().
                statusCode(200).
                root("_links.%s.href").
                body(withArgs("self"), notNullValue()).
                body(withArgs("list-robots"), endsWith("/robots")).
                body(withArgs("list-rooms"), endsWith("/rooms"));
    }

    // @formatter:on

}