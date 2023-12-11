package org.kosta.starducks.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MenuInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("reqURI." + request.getRequestURI());
        String uri = request.getRequestURI().substring(1);

        String menu = "";
        String subMenu = "";

        if(uri.contains("/")) {
            menu = uri.substring(0, uri.indexOf("/"));
        }
//        String mess = uri.substring(uri.indexOf("/"));
//        String subMenu = mess.substring(1, mess.indexOf("/"));

        System.out.printf("menu: %s", menu);

    }

    public String getMenu() {

        return null;
    }
    public String getSubMenu() {

        return null;
    }
}
