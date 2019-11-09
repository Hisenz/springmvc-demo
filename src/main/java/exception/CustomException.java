package exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaihs
 * @date 2019/11/9
 */
public class CustomException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String exceptionName = ex.getClass().getName();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exceptionName", exceptionName);
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
