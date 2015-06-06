package com.liangzhi.mercury.message.handlers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liangzhi.mercury.message.MessageDecoder;

/**
 * Just a dummy protocol mainly to show the ServerBootstrap being initialized.
 * 
 * @author Abraham Menacherry
 * 
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    
    /**
     * This integer represents the maximum size of a TCP packet we are allowing
     */
    private static final int MAX_FRAME_SIZE = 16384;

    @Autowired
    StringDecoder stringDecoder;

    @Autowired
    MessageDecoder messageDecoder;

    @Autowired
    InboundMessageHandler messageHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(MAX_FRAME_SIZE, Delimiters.lineDelimiter()));
        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(MAX_FRAME_SIZE, Delimiters.nulDelimiter()));
        pipeline.addLast("stringDecoder", stringDecoder);
        
        // business logic
        pipeline.addLast("messageDecoder", messageDecoder);
        pipeline.addLast("messageHandler", messageHandler);
    }

    public StringDecoder getStringDecoder() {
        return stringDecoder;
    }

    public void setStringDecoder(StringDecoder stringDecoder) {
        this.stringDecoder = stringDecoder;
    }

    public MessageDecoder getMessageDecoder() {
        return messageDecoder;
    }

    public void setMessageDecoder(MessageDecoder messageDecoder) {
        this.messageDecoder = messageDecoder;
    }

    public InboundMessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(InboundMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}