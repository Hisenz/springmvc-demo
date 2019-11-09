package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.User;
import pojo.VO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhaihs
 * @date 2019/11/8
 */
@Controller
@RequestMapping("/request")
public class RequestParamController {

    /**
     * 获取基本数据类型参数
     *
     * @param username
     * @param age
     * @return
     */
    @RequestMapping("/basicParam")
    @ResponseBody
    public HashMap basicParam(String username, int age) {
        return new HashMap(2) {{
            put("username", username);
            put("age", age);
        }};
    }

    /**
     * pojo 类型参数
     *
     * @param user
     * @return
     */
    @RequestMapping("/pojo")
    @ResponseBody
    public User pojoParam(User user) {
        return user;
    }

    /**
     * 数组类型参数
     *
     * @param strs
     * @return
     */
    @RequestMapping("/array")
    @ResponseBody
    public List<String> arrayParam(String[] strs) {
        return Arrays.asList(strs);
    }


    /**
     * 集合类型参数
     * 需将集合参数包装到一个POJO中
     *
     * @param vo
     */
    @RequestMapping("/list")
    @ResponseBody
    public VO listParam(VO vo) {
        return vo;
    }


    /**
     * 集合类型参数2
     * 当使用 Ajax 提交时， 可以指定 ContentType 为 json 形式
     * 那么在方法参数位置使用 @RequestBody可以直接接受集合数据而无需使用POJO进行包装
     *
     * @param userList
     * @return
     */
    @RequestMapping("/list2")
    @ResponseBody
    public List<User> listParam2(@RequestBody List<User> userList) {
        return userList;
    }


    /**
     * 参数绑定
     *
     * @param method
     * @return
     */
    @RequestMapping("/bind")
    @ResponseBody
    public String bindParam(@RequestParam(value = "way", required = false, defaultValue = "get") String method) {
        return "method: " + method;
    }

    /**
     * restful param
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/rest/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String restParam(@PathVariable int id) {
        return "id: " + id;
    }

    /**
     * 日期转换
     *
     * @param date
     * @return
     */
    @RequestMapping("/date")
    @ResponseBody
    public void date(Date date) {
        System.out.println(date);
    }

    /**
     * 获取Servlet相关API
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/servletApi")
    @ResponseBody
    public Object[] servletApi(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return new Object[]{request.toString()
                , response.toString()
                , session.toString()};
    }

    /**
     * 获取请求头参数
     *
     * @param user_Agent
     * @return
     */
    @RequestMapping("/header")
    @ResponseBody
    public String headerParam(@RequestHeader(value = "User-agent", required = false) String user_Agent) {
        return user_Agent;
    }

    /**
     * 获取cookie
     *
     * @param jSessionId
     * @return
     */
    @RequestMapping("/cookie")
    @ResponseBody
    public String getCookie(@CookieValue(value = "JSESSIONID") String jSessionId) {
        return jSessionId;
    }

    /**
     * 文件上传
     *
     * @param username
     * @param uploadFile
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(String username, MultipartFile uploadFile) throws IOException {
        System.out.println(username + " upload file " + uploadFile.getOriginalFilename());
        File file = new File("E:upload/" + uploadFile.getOriginalFilename());
        // save file
        uploadFile.transferTo(file);
        return file.getAbsolutePath();
    }
}
