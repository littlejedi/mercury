package com.liangzhi.mercury.message.payload;

import com.google.common.base.Objects;

public class SensorDataPoint {
    
    /**
     * Identifies a sensor, ID should be already generated for the user
     */
    private String sensorId;
    
    /**
     * For data type, this should be a double, for image and video, this should be the file path
     * Tentative, may delete this
     */
    private int sensorDataType;
    
    /**
     * Value of this data push
     */
    private String sensorDataValue;
    
    /**
     * UTC timestamp of when the data was collected
     */
    private long sensorDataTimestamp;

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public int getSensorDataType() {
        return sensorDataType;
    }

    public void setSensorDataType(int sensorDataType) {
        this.sensorDataType = sensorDataType;
    }

    public String getSensorDataValue() {
        return sensorDataValue;
    }

    public void setSensorDataValue(String sensorDataValue) {
        this.sensorDataValue = sensorDataValue;
    }

    public long getSensorDataTimestamp() {
        return sensorDataTimestamp;
    }

    public void setSensorDataTimestamp(long sensorDataTimestamp) {
        this.sensorDataTimestamp = sensorDataTimestamp;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("sensorId", sensorId)
                .add("sensorDataType", sensorDataType)
                .add("sensorDataValue", sensorDataValue)
                .add("sensorDataTimestamp", sensorDataTimestamp).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sensorId, sensorDataType, sensorDataValue,
                sensorDataTimestamp);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SensorDataPoint) {
            SensorDataPoint that = (SensorDataPoint) object;
            return Objects.equal(this.sensorId, that.sensorId)
                    && Objects.equal(this.sensorDataType, that.sensorDataType)
                    && Objects.equal(this.sensorDataValue, that.sensorDataValue)
                    && Objects.equal(this.sensorDataTimestamp, that.sensorDataTimestamp);
        }
        return false;
    }
}
