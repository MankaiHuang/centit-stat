package com.centit.stat.config;

import com.centit.fileserver.client.ClientAsFileStore;
import com.centit.fileserver.client.FileClientImpl;
import com.centit.framework.components.impl.NotificationCenterImpl;
import com.centit.framework.components.impl.TextOperationLogWriterImpl;
import com.centit.framework.config.SpringSecurityCasConfig;
import com.centit.framework.config.SpringSecurityDaoConfig;
import com.centit.framework.ip.app.config.IPOrStaticAppSystemBeanConfig;
import com.centit.framework.jdbc.config.JdbcConfig;
import com.centit.framework.model.adapter.NotificationCenter;
import com.centit.framework.model.adapter.OperationLogWriter;
import com.centit.framework.security.model.CentitPasswordEncoder;
import com.centit.framework.security.model.StandardPasswordEncoderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by codefan on 17-7-18.
 */
@ComponentScan(basePackages = "com.centit",
        excludeFilters = @ComponentScan.Filter(value = org.springframework.stereotype.Controller.class))
@Import({SpringSecurityDaoConfig.class,
    SpringSecurityCasConfig.class,
    IPOrStaticAppSystemBeanConfig.class,
    JdbcConfig.class})
@Configuration
public class ServiceConfig {

    @Value("${datapacket.buff.enabled:false}")
    private boolean dataBuffEnable;

    @Value("${app.home:./}")
    private String appHome;

    @Value("${redis.home:192.168.134.9}")
    private String redisHost;

    @Value("${redis.port:6379}")
    private int redisPort;
    @Value("${fileserver.url}")
    private String fileserver;

    @Bean(name = "passwordEncoder")
    public CentitPasswordEncoder centitPasswordEncoder(){
        return new StandardPasswordEncoderImpl();
    }

    @Bean
    public JedisPool jedisPool(){
        if(!dataBuffEnable){
            return null;
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1024);
        config.setMaxIdle(200);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        return new JedisPool(config, redisHost, redisPort, 10000);
    }

    @Bean
    public NotificationCenter notificationCenter() {
        NotificationCenterImpl notificationCenter = new NotificationCenterImpl();
        notificationCenter.initDummyMsgSenders();
        //notificationCenter.registerMessageSender("innerMsg",innerMessageManager);
        return notificationCenter;
    }

    @Bean
    @Lazy(value = false)
    public OperationLogWriter operationLogWriter() {
        TextOperationLogWriterImpl operationLog = new TextOperationLogWriterImpl();
        operationLog.setOptLogHomePath(appHome + "/logs");
        operationLog.init();
        return operationLog;
    }

    @Bean
    public InstantiationServiceBeanPostProcessor instantiationServiceBeanPostProcessor() {
        return new InstantiationServiceBeanPostProcessor();
    }

    @Bean
    public FileClientImpl fileClient() {
        FileClientImpl fileClient = new FileClientImpl();
        fileClient.init(fileserver,fileserver,"u0000000", "000000",fileserver);
        return fileClient;
    }


    @Bean
    public ClientAsFileStore fileStore(@Autowired FileClientImpl fileClient){
        ClientAsFileStore fileStoreBean = new ClientAsFileStore();
        fileStoreBean.setFileClient(fileClient);
        return fileStoreBean;
    }
}

