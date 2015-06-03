package com.liangzhi.mercury.elastic.model;

import java.util.UUID;

import com.google.common.base.Objects;

/**
 * Represents a Elastic search document for a sensor data point
 * @author Littlejedi
 *
 */
public class SensorDataPointDocument {

    private String id;

    private String sensorId;

    private String deviceId;

    private int sensorDataType;

    // For data type, this should be a double, for image and video, this should be the file path
    private String sensorDataValue;
    
    private long sensorDataTimestamp;
    
    public SensorDataPointDocument(String sensorId, String deviceId, int sensorDataType, String sensorDataValue, long sensorDataTimestamp) {
        this.id = UUID.randomUUID().toString();
        this.deviceId = deviceId;
        this.sensorId = sensorId;
        this.sensorDataType = sensorDataType;
        this.sensorDataValue = sensorDataValue;
        this.sensorDataTimestamp = sensorDataTimestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getSensorDataType() {
        return sensorDataType;
    }

    public void setSensorDataType(int sensorDataType) {
        this.sensorDataType = sensorDataType;
    }

    public long getSensorDataTimestamp() {
        return sensorDataTimestamp;
    }

    public void setSensorDataTimestamp(long sensorDataTimestamp) {
        this.sensorDataTimestamp = sensorDataTimestamp;
    }
    
    public String getSensorDataValue() {
        return sensorDataValue;
    }

    public void setSensorDataValue(String sensorDataValue) {
        this.sensorDataValue = sensorDataValue;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id)
                .add("sensorId", sensorId).add("deviceId", deviceId)
                .add("sensorDataType", sensorDataType)
                .add("sensorDataValue", sensorDataValue)
                .add("sensorDataTimestamp", sensorDataTimestamp).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, sensorId, deviceId, sensorDataType, sensorDataValue, sensorDataTimestamp);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SensorDataPointDocument) {
            SensorDataPointDocument that = (SensorDataPointDocument) object;
            return Objects.equal(this.id, that.id)
                    && Objects.equal(this.sensorId, that.sensorId)
                    && Objects.equal(this.deviceId, that.deviceId)
                    && Objects.equal(this.sensorDataType, that.sensorDataType)
                    && Objects.equal(this.sensorDataValue, that.sensorDataValue)
                    && Objects.equal(this.sensorDataTimestamp, that.sensorDataTimestamp);
        }
        return false;
    }
}