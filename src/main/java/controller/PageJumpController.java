package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhaihs
 * @date 2019/11/8
 */
@Controller
@RequestMapping("/pageJump")
public class PageJumpController {
    /**
     * 直接跳转
     *
     * @return
     */
    @RequestMapping("/direct")
    public String direct() {
        return "target";
    }

    @RequestMapping("/modelAndView1")
    public ModelAndView modelAndViewMethod1() {
        /*
        Model(模型)：封装数据
        View(视图)：展示数据
         */
        ModelAndView modelAndView = new ModelAndView();

        // 设置模型数据
        modelAndView.addObject("message", "method1");

        // 设置视图名称
        modelAndView.setViewName("target");

        return modelAndView;
    }

    @RequestMapping("/modelAndView2")
    public ModelAndView modelAndViewMethod2(ModelAndView modelAndView) {
        modelAndView.addObject("message", "method2");
        modelAndView.setViewName("target");
        return modelAndView;
    }

    @RequestMapping("/modelAndView3")
    public String modelAndViewMethod3(Model model) {
        model.addAttribute("message", "method3");
        return "target";
    }
}
