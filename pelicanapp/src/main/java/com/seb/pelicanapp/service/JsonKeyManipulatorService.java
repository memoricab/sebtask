package com.seb.pelicanapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This service manipulates given json's key to desired key for single json node. And returns Json Object Node.
 */

@Service
public class JsonKeyManipulatorService {
    private static Logger logger = LoggerFactory.getLogger(JsonKeyManipulatorService.class);

    /**
     * @param json        String to manipulate its key
     * @param keyToChange String Key to change to new value
     * @param newKey      String New key value to change old key value
     * @return Manipulated Json Object
     * @throws IOException
     */
    public ObjectNode replaceKey(String json, String keyToChange, String newKey) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        ObjectNode object;
        node = mapper.readTree(json); // Read string as json
        object = (ObjectNode) node; // Assign node as an object to handle assignments.
        object.set(newKey, node.get(keyToChange)); // Add new key to json with value of old key from the response json.
        object.remove(keyToChange); // Remove old key translation from the json object
        logger.info("Sucessfully manipulated response data translation key to data.");
        return object;
    }
}
