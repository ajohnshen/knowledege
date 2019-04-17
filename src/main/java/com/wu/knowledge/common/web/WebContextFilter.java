package com.wu.knowledge.common.web;


import com.wu.knowledge.common.log.MyLog;
import com.wu.knowledge.common.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 过滤器
 * Created by TZ on 2018/9/11.
 */
@WebFilter
public class WebContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MyLog.info("本次请求路径为："+httpServletRequest.getRequestURI() + "?"
                + httpServletRequest.getQueryString());
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        StringBuilder queryString = new StringBuilder();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (String value : values) {
                queryString.append(key).append("=").append(value).append("&");
            }
        }
        if (MyUtils.StringIsNotNull(queryString.toString())) {
            // 去掉最后一个空格
            queryString = new StringBuilder(queryString.substring(0, queryString.length() - 1));
            MyLog.info("请求参数值：" + queryString);
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        chain.doFilter(request, httpServletResponse);
    }

    @Override
    public void destroy() {
    }
}
