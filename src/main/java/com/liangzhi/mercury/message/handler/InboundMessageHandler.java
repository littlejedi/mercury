package com.liangzhi.mercury.message.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.liangzhi.mercury.message.Message;

@Sharable
@Component
public class InboundMessageHandler extends SimpleChannelInboundHandler<Message> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InboundMessageHandler.class);
    
    @Autowired
    private MessageHandlerConfiguration messageHandlerConfiguration;
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Lost Connection with SocketAddress={}", ctx.channel().remoteAddress());
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Lost Connection with SocketAddress={}", ctx.channel().remoteAddress());
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message)
            throws Exception {
        Preconditions.checkNotNull("Message should not be null", message);
        LOGGER.info("Received Message={}", message.toString());
        doProcessMessage(message, ctx);
        
    }
    
    private void doProcessMessage(Message msg, ChannelHandlerContext ctx) throws Exception {
        // Get handler, and process the message
        MessageHandler handler = messageHandlerConfiguration.getMessageHandler(msg.getType());
        handler.handleMessage(msg, ctx);
    }
    
    /*@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        marsErrorHandler.handleError(ctx, cause);
    }*/

}
