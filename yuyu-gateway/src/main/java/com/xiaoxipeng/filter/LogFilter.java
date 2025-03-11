package com.xiaoxipeng.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

@Component
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 记录请求开始时间
        long startTime = System.currentTimeMillis();

        // 获取请求信息
        String requestURL = request.getRequestURL().toString();
        String method = request.getMethod();

        // 美化的日志分隔符
        String separator = "┌─────────────────────────────────────────────────────────────────────────────────┐";
        String endSeparator = "└─────────────────────────────────────────────────────────────────────────────────┘";
        String midSeparator = "├─────────────────────────────────────────────────────────────────────────────────┤";

        // 记录请求开始
        log.info(separator);
        log.info("│ 🚀 请求开始: {} - {}", method, requestURL);

        // 记录请求头
        log.info(midSeparator);
        log.info("│ 📋 请求头信息:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("│    {} : {}", headerName, request.getHeader(headerName));
        }

        // 执行过滤器链
        filterChain.doFilter(servletRequest, servletResponse);

        // 记录响应头
        log.info(midSeparator);
        log.info("│ 📋 响应头信息:");
        Collection<String> responseHeaderNames = response.getHeaderNames();
        for (String headerName : responseHeaderNames) {
            log.info("│    {} : {}", headerName, response.getHeader(headerName));
        }

        // 记录请求处理时间
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info(midSeparator);
        log.info("│ ⏱️ 请求处理时间: {}ms", duration);
        log.info("│ 🏁 请求结束: {} - {}", method, requestURL);
        log.info(endSeparator);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
