package com.liangzhi.mercury.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageDeserializer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDeserializer.class);
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    private MessageDeserializer() {
        //Hidden on purpose
    }
    
    public static Message fromJSON(String json) throws Exception {
        try {
            return MAPPER.readValue(json, Message.class);
        } catch (Exception e) {
            LOGGER.error("Error deserializing incoming JSON to Message. JSON:{}", json);
            // Propagate the exception here so the error handler will handle it
            throw e;
        }
    }
    
    public ObjectMapper getObjectMapper() {
        return MAPPER;
    }
}
