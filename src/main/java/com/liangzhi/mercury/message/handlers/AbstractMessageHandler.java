package com.liangzhi.mercury.message.handlers;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;
import com.liangzhi.commons.domain.platform.User;
import com.liangzhi.mercury.message.Message;
import com.liangzhi.mercury.service.UserService;

public abstract class AbstractMessageHandler implements MessageHandler {
    
    @Autowired
    private UserService userService;

    @Override
    public void handleMessage(Message message, ChannelHandlerContext ctx)
            throws Exception {
        // Check if username and developer token matches
        User user = userService.findUserByEmail(message.getUsername());
        // Essential message properties should have already been validated in MessageDecoder
        // Logger should reflect the real handler class that's handling the message
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
}
