package ru.otus.novikov.java.hw9;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@WebFilter(urlPatterns = "/*")
public class LogFilterServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(LogFilterServlet.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        logger.info(new Date() + " - " + httpServletRequest.getServletPath());
        httpServletRequest.getParameterMap()
            .forEach((key, value) -> logger.info(String.format("Parameter: %s with values:%s", key, Arrays.toString(value))));

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
