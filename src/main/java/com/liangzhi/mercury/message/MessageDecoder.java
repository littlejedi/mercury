package com.liangzhi.mercury.message;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
@Component
public class MessageDecoder extends MessageToMessageDecoder<String> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, String str,
            List<Object> out) throws Exception {
        LOGGER.info("Received raw string={}",str);
        Message message = MessageDeserializer.fromJSON(str);
        validateMessage(message);
        out.add(message);
    }
    
    /*@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        marsErrorHandler.handleError(ctx, cause);
    }*/
    
    private void validateMessage(Message message) {
        // Validate message
        Preconditions.checkNotNull("Message id should not be null", message.getId());
        Preconditions.checkNotNull("Username should not be null", message.getUsername());
        Preconditions.checkNotNull("User developer token should not be null", message.getDeveloperToken());
        Preconditions.checkNotNull("Device id should not be null", message.getDeviceId());
        Preconditions.checkNotNull("Message created timestamp should not be null", message.getCreated());
        Preconditions.checkNotNull("Message type should not be null", message.getType());
    }

}
