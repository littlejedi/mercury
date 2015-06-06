package com.liangzhi.mercury.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.liangzhi.mercury.elastic.ElasticSearchClient;
import com.liangzhi.mercury.message.handlers.ServerChannelInitializer;


/**
 * This class contains the bean definitions for this netty server. These beans
 * are autowired into the respective java classes in org.nerdronix.* packages
 * using component scan feature of spring. Properties are injected using the
 * PropertySource. For more information look <a href=
 * "http://static.springsource.org/spring/docs/3.2.2.RELEASE/javadoc-api/org/springframework/context/annotation/Configuration.html"
 * >here</a> and <a href=
 * "http://blog.springsource.com/2011/06/10/spring-3-1-m2-configuration-enhancements/"
 * >here</a>
 * 
 * @author Abraham Menacherry
 * 
 */
@Configuration
@ImportResource("classpath:spring/applicationContext.xml")
@ComponentScan("com.liangzhi")
@PropertySource("classpath:env/default.properties")
@PropertySource("classpath:env/${env}.properties")
public class SpringConfig {

    @Value("${boss.thread.count}")
    private int bossCount;

    @Value("${worker.thread.count}")
    private int workerCount;

    @Value("${tcp.port}")
    private int tcpPort;

    @Value("${so.keepalive}")
    private boolean keepAlive;

    @Value("${so.backlog}")
    private int backlog;
    
    @Value("${elastic.host}")
    private String elasticHost;
    
    @Value("${elastic.port}")
    private int elasticPort;
    
    @Value("${log4j.configuration}")
    private String log4jConfiguration;

    @Autowired
    private ServerChannelInitializer serverChannelInitalizer;

    @SuppressWarnings("unchecked")
    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(serverChannelInitalizer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (@SuppressWarnings("rawtypes")
        ChannelOption option : keySet) {
            b.option(option, tcpChannelOptions.get(option));
        }
        return b;
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }

    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backlog);
        return options;
    }

    @Bean(name = "stringDecoder")
    public StringDecoder stringDecoder() {
        return new StringDecoder(CharsetUtil.UTF_8);
    }
    
    @Bean(name = "elasticSearchClient")
    public ElasticSearchClient elasticSearchClient() {
        return new ElasticSearchClient(this.elasticHost, this.elasticPort);
    }

    /**
     * Necessary to make the Value annotations work.
     * 
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
}