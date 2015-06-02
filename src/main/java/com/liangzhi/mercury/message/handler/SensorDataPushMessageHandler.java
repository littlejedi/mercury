package com.liangzhi.mercury.message.handler;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.liangzhi.mercury.message.Message;
import com.liangzhi.mercury.message.MessageDeserializer;
import com.liangzhi.mercury.message.MessageType;
import com.liangzhi.mercury.message.payload.SensorDataPush;

@Component
public class SensorDataPushMessageHandler extends AbstractMessageHandler {
    
    private Logger LOGGER = LoggerFactory.getLogger(SensorDataPushMessageHandler.class);

    @Override
    public MessageType getHandledType() {
        return MessageType.SENSOR_DATA_PUSH;
    }

    @Override
    protected Optional<Message> doHandleMessage(Message message,
            ChannelHandlerContext ctx) throws Exception {
        Preconditions.checkArgument(message.getPayload() != null, "Payload should NOT be null");
        //List<SensorDataRecord> records = MessageDeserializer.deserializeSensorDataSyncPayload(message.getPayloadAsRawJson());
        //LOGGER.info("Message payload successfully deserialized and converted into a list of SensorDataRecords of size={}", records.size());
        // We do not need to reply to this message
        return Optional.<Message>absent();
    }
    
    private List<SensorDataPush> deserializePayload(String rawJson) {
        List<SensorDataPush> records = new ArrayList<SensorDataPush>();
        return records;
    }

}
