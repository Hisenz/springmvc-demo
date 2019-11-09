package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhaihs
 * @date 2019/11/9
 */
@Controller
@RequestMapping("/interceptor")
public class InterceptorController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello, this is interceptor";
    }
}
