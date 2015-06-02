package com.liangzhi.mercury.message.handler;

import com.liangzhi.mercury.message.Message;
import com.liangzhi.mercury.message.MessageType;

import io.netty.channel.ChannelHandlerContext;

/**
 * Generic Interface for a Message Handler
 * @author Littlejedi
 *
 */
public interface MessageHandler {
    
    /**
     * The original message to handle
     * @param message
     */
    void handleMessage(Message message, ChannelHandlerContext ctx) throws Exception;
    
    /**
     * Returns the type this handler handles
     * @return
     */
    MessageType getHandledType();
}

