package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhaihs
 * @date 2019/11/8
 */
@Controller
public class QuickController {

    @RequestMapping("/hello")
    public String hello() {
        return "index.jsp";
    }
}
