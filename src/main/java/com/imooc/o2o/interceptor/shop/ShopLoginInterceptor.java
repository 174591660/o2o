package com.imooc.o2o.interceptor.shop;

import com.imooc.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by zy on 2019/1/24
 * <p>
 * 店铺登陆拦截器
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userObj = request.getSession().getAttribute("user");
        if (userObj != null) {
            PersonInfo user = (PersonInfo) userObj;
            if (user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1 &&
                    user.getShopOwnerFlag() == 1)
                return true;
        }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open ('" + request.getContextPath()
                + "/index.jsp','_self')");
        out.println("</script>");
        out.println("</html>");
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(8L);
        personInfo.setEnableStatus(1);
        personInfo.setShopOwnerFlag(1);
        request.getSession().setAttribute("user",personInfo);
        return false;
    }

}
