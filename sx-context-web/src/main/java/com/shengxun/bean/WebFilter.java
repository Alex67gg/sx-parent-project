package com.shengxun.bean;
import com.shengxun.result.JsonResult;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 拦截所有
 */
@javax.servlet.annotation.WebFilter(filterName = "respFilter",urlPatterns = "/*")
public class WebFilter implements Filter {
   public static  final Logger logger = Logger.getLogger("web");
    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("=======================filter初始化==========================");
    }

    /**
     * 处理返回码  错误为500的问题；
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

        HttpServletResponse response = null;
        try {
            response = (HttpServletResponse) servletResponse;
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            JsonResult.setResponse(response);
            filterChain.doFilter(request, response);
        }catch (Exception e){
            logger.info("filter拦截异常："+e.getMessage());
            response.setStatus(500);
        }
    }

    @Override
    public void destroy() {
    }
}
