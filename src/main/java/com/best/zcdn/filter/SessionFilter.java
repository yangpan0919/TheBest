package com.best.zcdn.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: zhuhaiwei
 * Date: 2016/11/4
 * Project: cs-platform
 * Description:
 */
public class SessionFilter implements javax.servlet.Filter {


    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 不需要session用户校验的接口
     */
    private String  excludeUrl = null;
    private String [] excludeUrls = {".*\\.js$",".*\\.gif$",".*\\.jpg$",".*\\.png$",".*\\.css$",".*\\.ico$","/login.html"};
    private String testIps;
    private java.util.Properties myApp = new java.util.Properties();
    private String testMockUrl = "";
    private String appEnv = "unknown";
    private boolean isTestEvn = false;  //是否测试环境
    private String requestHeaderValidation = null;

//    private JedisHanderComponent jedisHanderComponent;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        excludeUrl = filterConfig.getInitParameter("exclusions");
//
//
//        requestHeaderValidation = filterConfig.getInitParameter("requestHeaderValidation");
//
//        try {
//            java.io.InputStream is = this.getClass().getResourceAsStream("/myApp.properties");
//            if (is != null) {
//                myApp.load(is);
//            }
//        } catch (Exception e) {
//            logger.error("load myApp.properties failed!", e);
//        }
//
//        testMockUrl = myApp.getProperty("app.test.mock.url");
//        appEnv = myApp.getProperty("app.env");
//        //获取当前应用所在的环境，测试还是生产
//
//        if (appEnv != null && ("local".equalsIgnoreCase(appEnv) ||
//                "dev".equalsIgnoreCase(appEnv) ||
//                "test".equalsIgnoreCase(appEnv))) {
//            isTestEvn = true;
//        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpServletRequest.getRequestURI();
        httpServletRequest.getSession().setAttribute("userID","007");
//      ***  =  WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext()).getBean(***.class);
//        if(matchURI(excludeUrl,requestURI)){
//
//            if(httpServletRequest.getSession().getAttribute("userID") == null){
//                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.html");
//                return;
//            }
//        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        return;
    }

    @Override
    public void destroy() {

    }

    protected boolean matchURI(String excludeUrl, String url) {

        for (int i = 0; i < excludeUrls.length; i++) {
            if (url.matches(excludeUrls[i])) {
                return false;
            }
        }
        return true;
    }
}
