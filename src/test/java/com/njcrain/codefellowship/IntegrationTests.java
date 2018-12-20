package com.njcrain.codefellowship;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    //Some of this came from https://www.baeldung.com/rest-template along with various spring docs
    @Test
    public void testHome() {
        ResponseEntity<String> response =  this.restTemplate.getForEntity("http://localhost:" + port + "/", String.class);
        assertEquals("The response code should be 200", 200, response.getStatusCodeValue());
        assertTrue("Somewhere in the respons should be the header text of 'Welcom to CodeFellowShip", response.toString().contains("<h1>Welcome to CodeFellowShip</h1>"));
    }

    @Test
    public void testSignUp() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/signup", String.class);
        assertEquals("The server should give back a 200 response", 200, response.getStatusCodeValue());
        assertTrue("There should be a form that makes a post to /signup within the page", response.toString().contains("<form action=\"/signup\" method=\"post\">"));
    }

    @Test
    public void testLogin() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/login", String.class);
        assertEquals("The server should give back a 200 response", 200, response.getStatusCodeValue());
        assertTrue("There should be a form that makes a post to /perform_login within the page", response.toString().contains("<form action=\"/perform_login\" method=\"post\">"));
    }
}
