package com.seb.pelicanapp;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.seb.pelicanapp.service.LetterCapitalizorService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class LetterCapitalizorServiceTests {
    private LetterCapitalizorService letterCapitalizorService;

    @Before
    public void setup() {
        letterCapitalizorService = new LetterCapitalizorService();
    }

    @Test
    public void testIfCapitalizesWithTheGivenParamaters() throws IOException {
        String json = "{\"hello\":\"world\"}";
        ObjectNode jsonObject = (ObjectNode) new ObjectMapper().readTree(json);
        jsonObject = letterCapitalizorService.capitalizeLetter(jsonObject,"hello",0);

        assertThat(jsonObject.get("hello").asText()).isEqualTo("World");

    }

    @Test
    public void testIfCapitalizesWithTheGivenParamaters_whenGivenJsonHasMoreThanOneKeys() throws IOException {
        String json = "{\"hello\":\"world\",\"tere\":\"car\"}";
        ObjectNode jsonObject = (ObjectNode) new ObjectMapper().readTree(json);
        jsonObject= letterCapitalizorService.capitalizeLetter(jsonObject, "tere", 1);
        jsonObject= letterCapitalizorService.capitalizeLetter(jsonObject, "hello", 3);

        assertThat(jsonObject.get("hello").asText()).isEqualTo("worLd");
        assertThat(jsonObject.get("tere").asText()).isEqualTo("cAr");
    }
}
