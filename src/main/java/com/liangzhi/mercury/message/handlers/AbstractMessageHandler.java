package com.liangzhi.mercury.message.handlers;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.liangzhi.mercury.message.Message;

public abstract class AbstractMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(Message message, ChannelHandlerContext ctx)
            throws Exception {
        Preconditions.checkArgument(message != null, "Message should not be null.");
        Preconditions.checkArgument(message.getDeviceId() != null, "Device ID should not be null");
        Preconditions.checkArgument(message.getDeviceKey() != null, "Device ID should not be null");
        // logger should reflect the real handler class that's handling the message
        final Logger currentHandlerLogger = LoggerFactory.getLogger(this.getClass());
        currentHandlerLogger.info("Handling Message={}", message);
        Optional<Message> replyMessage = doHandleMessage(message, ctx);
        if (replyMessage.isPresent()) {
            currentHandlerLogger.info("Reply message is present for messageId={}, replyMessageId={}", message.getId(), replyMessage.get().getId());
            reply(replyMessage.get(), ctx);
        }
        currentHandlerLogger.info("Successfully handled messageId={}", message.getId());       
    }

    /**
     * Do the real processing of the message, and return a reply message if necessary
     * @param message
     * @param ctx
     * @return Optional reply message
     */
    protected abstract Optional<Message> doHandleMessage(Message message, ChannelHandlerContext ctx) throws Exception;
    
    /**
     * Replys a message by sending it to the Netty outbound handler chain
     * @param replyMessage
     * @param ctx
     */
    protected void reply(Message replyMessage, ChannelHandlerContext ctx) {
        //messageGateway.sendMessage(replyMessage, ctx);
    }
    
    /**
     * Deserialize the payload specific to this message type
     * @throws Exception
     */
    protected void deserializePayload() throws Exception
    {
        // Do nothing
    }

}
