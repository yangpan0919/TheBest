package com.best.zcdn.config;

import com.best.zcdn.filter.MyFilter;
import com.best.zcdn.filter.SessionFilter;
import com.best.zcdn.listener.MyListener;
import com.best.zcdn.servlet.MyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;

@Configuration
public class MyServerConfig {

    //注册三大组件
    @Bean
    public ServletRegistrationBean myServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(),"/myServlet");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/emp","/myServlet"));
        //执行的顺序之类的（order的数值越小，优先级越高）
        registrationBean.setOrder(6);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean sessionFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/login.html");
        registrationBean.setFilter(new SessionFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/*"));

        //执行的顺序之类的（order的数值越小，优先级越高）
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
        return registrationBean;
    }


    //配置嵌入式的Servlet容器
//    @Bean
//    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
//        return new EmbeddedServletContainerCustomizer() {
//
//            //定制嵌入式的Servlet容器相关的规则
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//                container.setPort(8083);
//            }
//        };
//    }

}
