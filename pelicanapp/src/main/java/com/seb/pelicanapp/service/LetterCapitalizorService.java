package com.seb.pelicanapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This service capitalize the given key's value of json letter with given index.
 */
@Service
public class LetterCapitalizorService {


    /**
     * @param json  Json object node to manipulate
     * @param key   is representing which value of key will be changed
     * @param index is representing which index of value will be changed
     * @return Returns manipulated json object
     * @throws IOException
     */
    public ObjectNode capitalizeLetter(ObjectNode json, String key, int index) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String word = json.get(key).asText();
        JsonNode manipulatedNode = mapper.readTree("\"" +word.replace(word.charAt(index),Character.toUpperCase(word.charAt(index)))+ "\"");
        return (ObjectNode) json.set(key, manipulatedNode);
    }
}
