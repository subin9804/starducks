package org.kosta.starducks.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.kosta.starducks.commons.menus.MenuDetail;
import org.kosta.starducks.commons.menus.MenuService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component("menu")
@Getter
public class MenuInterceptor implements HandlerInterceptor {

    private String menu;
    private String subMenu;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("reqURI." + request.getRequestURI());
        String uri = request.getRequestURI().substring(1);

//        String menu = "";
//        String subMenu = "";

        // 잘라내고 남은 uri
        String mess = "";

        if(uri.contains("/")) {
            menu = uri.substring(0, uri.indexOf("/"));
            mess = uri.substring(uri.indexOf("/") + 1);
        } else {
            menu = uri;
            mess = "";
        }

        if(mess.contains("/")) {
            subMenu = mess.substring(0, mess.indexOf("/"));
        } else {
            subMenu = mess;
        }

//        String mess = uri.substring(uri.indexOf("/"));
//        String subMenu = mess.substring(1, mess.indexOf("/"));

        System.out.printf("menu: %s, mess: %s, subMenu: %s", menu, mess, subMenu, '\n');
        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("reqURI." + request.getRequestURI());
//        String uri = request.getRequestURI().substring(1);
//
////        String menu = "";
////        String subMenu = "";
//
//        // 잘라내고 남은 uri
//        String mess = "";
//
//        if(uri.contains("/")) {
//            menu = uri.substring(0, uri.indexOf("/"));
//            mess = uri.substring(uri.indexOf("/") + 1);
//        } else {
//            menu = uri;
//            mess = "";
//        }
//
//        if(mess.contains("/")) {
//            subMenu = mess.substring(0, mess.indexOf("/"));
//        } else {
//            subMenu = mess;
//        }
//
////        String mess = uri.substring(uri.indexOf("/"));
////        String subMenu = mess.substring(1, mess.indexOf("/"));
//
//        System.out.printf("menu: %s, mess: %s, subMenu: %s", menu, mess, subMenu);
//
//    }

    public List<MenuDetail> getSubList() {

        return MenuService.gets(menu);
    }

}
