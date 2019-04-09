package com.centit.stat.config;

import com.centit.framework.config.SystemSpringMvcConfig;
import com.centit.framework.config.WebConfig;
import com.centit.support.file.PropertiesReader;
import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Properties;

/**
 * Created by zou_wy on 2017/3/29.
 */

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        initializeSpringConfig(servletContext);
        initializeSystemSpringMvcConfig(servletContext);
        initializeSpringMvcConfig(servletContext);
        initializeMetaSpringMvcConfig(servletContext);
        initializePageSpringMvcConfig(servletContext);
        initializeDataPacketSpringMvcConfig(servletContext);

        WebConfig.registerRequestContextListener(servletContext);
        WebConfig.registerSingleSignOutHttpSessionListener(servletContext);
        WebConfig.registerCharacterEncodingFilter(servletContext);
        WebConfig.registerHttpPutFormContentFilter(servletContext);
        WebConfig.registerHiddenHttpMethodFilter(servletContext);
        WebConfig.registerRequestThreadLocalFilter(servletContext);
        WebConfig.registerSpringSecurityFilter(servletContext);

        Properties properties = PropertiesReader.getClassPathProperties("/system.properties");
        String jdbcUrl = properties.getProperty("jdbc.url");

        if(jdbcUrl.startsWith("jdbc:h2")){
            initializeH2Console(servletContext);
        }
    }

    /**
     * 加载Spring 配置
     * @param servletContext ServletContext
     */
    private void initializeSpringConfig(ServletContext servletContext){
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(ServiceConfig.class);
        servletContext.addListener(new ContextLoaderListener(springContext));
    }

    /**
     * 加载Servlet 配置
     * @param servletContext ServletContext
     */
    private void initializeSystemSpringMvcConfig(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SystemSpringMvcConfig.class, SwaggerConfig.class);
        ServletRegistration.Dynamic system  = servletContext.addServlet("system", new DispatcherServlet(context));
        system.addMapping("/system/*");
        system.setLoadOnStartup(1);
        system.setAsyncSupported(true);
    }

    /**
     * 加载Servlet 项目配置
     * @param servletContext ServletContext
     */
    private void initializeSpringMvcConfig(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(NormalSpringMvcConfig.class, SwaggerConfig.class);
        ServletRegistration.Dynamic stat  = servletContext.addServlet("stat", new DispatcherServlet(context));
        stat.addMapping("/stat/*");
        stat.setLoadOnStartup(1);
        stat.setAsyncSupported(true);
    }

    private void initializeMetaSpringMvcConfig(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MetaDataSpringMvcConfig.class, SwaggerConfig.class);
        ServletRegistration.Dynamic metadata  = servletContext.addServlet("metadata", new DispatcherServlet(context));
        metadata.addMapping("/metadata/*");
        metadata.setLoadOnStartup(1);
        metadata.setAsyncSupported(true);
    }

    private void initializePageSpringMvcConfig(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(PageDesignSpringMvcConfig.class, SwaggerConfig.class);
        ServletRegistration.Dynamic page  = servletContext.addServlet("page", new DispatcherServlet(context));
        page.addMapping("/page/*");
        page.setLoadOnStartup(1);
        page.setAsyncSupported(true);
    }

    private void initializeDataPacketSpringMvcConfig(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(DataPacketSpringMvcConfig.class, SwaggerConfig.class);
        ServletRegistration.Dynamic datapacket  = servletContext.addServlet("datapacket", new DispatcherServlet(context));
        datapacket.addMapping("/datapacket/*");
        datapacket.setLoadOnStartup(1);
        datapacket.setAsyncSupported(true);
    }

    /**
     * 访问 h2 console
     * @param servletContext ServletContext
     */
    private void initializeH2Console(ServletContext servletContext){
        AnnotationConfigWebApplicationContext contextSer = new AnnotationConfigWebApplicationContext();
        contextSer.register(NormalSpringMvcConfig.class);
        contextSer.setServletContext(servletContext);
        ServletRegistration.Dynamic h2console  = servletContext.addServlet("h2console", WebServlet.class);
        h2console.setInitParameter("webAllowOthers", "");
        h2console.addMapping("/console/*");
        h2console.setLoadOnStartup(1);
        h2console.setAsyncSupported(true);
    }
}
