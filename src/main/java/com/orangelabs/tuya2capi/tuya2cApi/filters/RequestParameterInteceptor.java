package com.orangelabs.tuya2capi.tuya2cApi.filters;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class RequestParameterInteceptor extends HandlerInterceptorAdapter
{
    private static final Logger LOG = Logger.getLogger(RequestParameterInteceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {

        String method = request.getMethod();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        String path = request.getRequestURL().toString();
        StringBuilder sb = new StringBuilder("\n******************the " + method + "  scheme    " + scheme + "    port    " + port + " request " + path
                                             + " parameters are ************************\n");
        sb.append("\tUser-Agent: " + request.getHeader("User-Agent") + "\n");
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements())
        {
            String name = en.nextElement();
            sb.append("\t parameter key name and value ---> " + name + " : " + request.getParameter(name) + "\n");
        }

        //LOG.info(sb.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView)
        throws Exception
    {
        //super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception
    {
        //super.afterCompletion(request, response, handler, ex);
    }


}
