package com.liangzhi.mercury.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Objects;

/**
 * Simple POJO that represents a cross-device Message
 * @author Littlejedi
 *
 */
public class Message {
    
    /**
     * Indicates the unique identifier of this message (32 digit UUID with dashes)
     */
    private String id;
    
    /**
     * Indicates the device key of this message - device key is unique for each device
     */
    private String deviceKey;
    
    /**
     * UTC timestamp of when the message was originated
     */
    private Long created;
    
    /**
     * Message type
     */
    private MessageType type;
    
    /**
     * Payload of the message (if applicable)
     */
    private Object payload;
    
    /**
     * Response code (if applicable)
     */
    private String responseCode;
    
    /**
     * Error Message (if applicable)
     */
    private String errorMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    /** Jackson deserializes this field to a JsonNode **/
    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }
    
    /** 
     * Use this method to get the raw JSON payload for deserialization 
     * Won't be used during serialization
     **/
    @JsonIgnore
    public String getPayloadAsRawJson() {
        return payload == null ? null : payload.toString();
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, deviceKey, created, type, payload,
                responseCode, errorMessage);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Message) {
            Message that = (Message) object;
            return Objects.equal(this.id, that.id)
                    && Objects.equal(this.deviceKey, that.deviceKey)
                    && Objects.equal(this.created, that.created)
                    && Objects.equal(this.type, that.type)
                    && Objects.equal(this.payload, that.payload)
                    && Objects.equal(this.responseCode, that.responseCode)
                    && Objects.equal(this.errorMessage, that.errorMessage);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id)
                .add("deviceKey", deviceKey).add("created", created)
                .add("type", type).add("payload", payload)
                .add("responseCode", responseCode)
                .add("errorMessage", errorMessage).toString();
    }
}