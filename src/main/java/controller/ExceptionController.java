package controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author zhaihs
 * @date 2019/11/9
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("/test")
    @ResponseBody
    public String testException() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        format.parse("adsf");

        return null;
    }
}
