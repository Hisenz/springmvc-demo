package interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author zhaihs
 * @date 2019/11/9
 */
public class CustomInterceptor implements HandlerInterceptor {
    /**
     * 检查请求参数列表 是否存在 name 不存在则拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String name = (String)request.getParameter("name");
        if (name != null && name.length() != 0) {
            return true;
        }
        response.getWriter().write("parameter is not found");
        response.setStatus(404);
        return false;
    }

    /**
     * 响应中添加 cookie interceptor
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("返回视图层执行");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器 执行结束！");
    }
}
