package com.liangzhi.mercury.message;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
@Component
public class MessageDecoder extends MessageToMessageDecoder<String> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg,
            List<Object> out) throws Exception {
        LOGGER.debug(msg);
    }
    
    /*@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        marsErrorHandler.handleError(ctx, cause);
    }*/

}
