package com.liangzhi.mercury.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.liangzhi.commons.mybatis.HasValueEnum;

/**
 * Represents the type of the message
 * @author Littlejedi
 *
 */
public enum MessageType implements HasValueEnum {
    
    INSTANT_SENSOR_DATA_SYNC(0);

    private int value;
    
    private MessageType(int value) {
        this.value = value;
    }
        
    @JsonCreator
    public static MessageType fromValue(int value) {
        for (MessageType t : MessageType.values()) {
            if (t.value == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown MessageType value passed: " + value);
    }

    @JsonValue
    @Override
    public int getValue() {
        return this.value;
    }
}
