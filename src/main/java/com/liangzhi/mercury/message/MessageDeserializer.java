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

}
