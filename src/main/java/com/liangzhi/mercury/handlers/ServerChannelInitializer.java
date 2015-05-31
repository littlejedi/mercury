package com.liangzhi.mercury.handlers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(MAX_FRAME_SIZE, Delimiters.nulDelimiter()));
        pipeline.addLast("stringDecoder", stringDecoder);
        
        // business logic
        pipeline.addLast("messageDecoder", messageDecoder);
        pipeline.addLast("handler", serverHandler);
    }

    public StringDecoder getStringDecoder() {
        return stringDecoder;
    }

    public void setStringDecoder(StringDecoder stringDecoder) {
        this.stringDecoder = stringDecoder;
    }

    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

}