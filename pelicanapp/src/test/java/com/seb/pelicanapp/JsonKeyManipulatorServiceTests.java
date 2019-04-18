package com.seb.pelicanapp;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.seb.pelicanapp.service.JsonKeyManipulatorService;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

public class JsonKeyManipulatorServiceTests {
    private JsonKeyManipulatorService jsonKeyManipulatorService;

    @Before
    public void setup() {
        jsonKeyManipulatorService = new JsonKeyManipulatorService();
    }

    @Test
    public void testIfKeyManipulatedWithTheGivenKey() throws IOException {
        String json = "{\"hello\":\"world\"}";
        ObjectNode manipulatedObject =  jsonKeyManipulatorService.replaceKey(json, "hello", "tere");

        assertThat(manipulatedObject.get("tere").asText()).isEqualTo("world");

    }

    @Test
    public void testIfKeyManipulatedWithTheGivenKey_whenGivenJsonHasMoreThanOneKeys() throws IOException {
        String json = "{\"hello\":\"world\",\"tere\":\"car\"}";
        ObjectNode manipulatedObject =  jsonKeyManipulatorService.replaceKey(json, "hello", "labas");

        assertThat(manipulatedObject.get("labas").asText()).isEqualTo("world");
        assertThat(manipulatedObject.get("tere").asText()).isEqualTo("car");
    }
}
