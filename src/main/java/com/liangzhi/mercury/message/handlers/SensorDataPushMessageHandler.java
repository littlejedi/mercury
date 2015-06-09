package com.liangzhi.mercury.message.handlers;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.liangzhi.mercury.elastic.ElasticSearchClient;
import com.liangzhi.mercury.elastic.model.SensorDataPointDocument;
import com.liangzhi.mercury.message.Message;
import com.liangzhi.mercury.message.MessageDeserializer;
import com.liangzhi.mercury.message.MessageType;
import com.liangzhi.mercury.message.payload.SensorDataPoint;

@Component
public class SensorDataPushMessageHandler extends AbstractMessageHandler {
    
    private Logger LOGGER = LoggerFactory.getLogger(SensorDataPushMessageHandler.class);
    
    @Autowired
    private ElasticSearchClient elasticSearchClient;

    @Override
    public MessageType getHandledType() {
        return MessageType.SENSOR_DATA_PUSH;
    }

    @Override
    protected Optional<Message> doHandleMessage(Message message,
            ChannelHandlerContext ctx) throws Exception {
        final String payload = message.getPayloadAsRawJson();
        Preconditions.checkArgument(message.getPayload() != null, "Payload should NOT be null");
        final List<SensorDataPointDocument> docs = processPayload(message.getDeviceId(), payload);
        // We do not need to reply to this message
        return Optional.<Message>absent();
    }
    
    private List<SensorDataPointDocument> processPayload(String deviceId, String payload) throws Exception {
        Preconditions.checkNotNull(payload, "Paylod should not be null!");
        try {
            SensorDataPoint[] dataPoints = MessageDeserializer.MAPPER.readValue(payload, SensorDataPoint[].class);
            List<SensorDataPointDocument> result = Lists.newArrayListWithCapacity(dataPoints.length);
            for (SensorDataPoint dataPoint : dataPoints) {
                validateSensorDataPoint(dataPoint);
                String sensorId = dataPoint.getSensorId();
                String sensorValue = dataPoint.getSensorDataValue();
                Long timestamp = dataPoint.getSensorDataTimestamp();
                int sensorDataType = dataPoint.getSensorDataType();
                SensorDataPointDocument doc = 
                        new SensorDataPointDocument(sensorId, deviceId, sensorDataType, sensorValue, timestamp);
                LOGGER.info("Indexed sensor data point document={}", doc.toString());               
            }
            return result;
        } catch (Exception e) {
            LOGGER.error(
                    "An exception occured during serialization. Payload={}",
                    payload);
            throw e;
        }
    }
    
    private void validateSensorDataPoint(SensorDataPoint dpt) throws Exception {
        Preconditions.checkNotNull(dpt.getSensorId(), "Sensor ID shoud not be null!");
        Preconditions.checkNotNull(dpt.getSensorDataValue(), "Sensor value should not be null!");
        Preconditions.checkNotNull(dpt.getSensorDataType(), "Sensor data type should not be null!");
    }

}
