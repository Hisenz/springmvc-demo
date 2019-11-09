package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author zhaihs
 * @date 2019/11/8
 */
@Controller
@RequestMapping("/dataBack")
public class DataBackController {

    private class User {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    @RequestMapping("/direct")
    @ResponseBody
    public String direct() {
        return "direct write back data";
    }

    @RequestMapping("/jsonString1")
    @ResponseBody
    public String backJson() {
        return "{\"type\":\"json\",\"message\":\"content\"}";
    }


    /**
     * 使用 Jackson 工匠将对象转化为 json 数据
     *
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/jsonString2")
    @ResponseBody
    public String backJson2() throws JsonProcessingException {
        HashMap<String, Integer> map = new HashMap<String, Integer>(2) {{
            put("number", 23);
            put("type", 10);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(map);
        return jsonStr;
    }

    /**
     * 直接返回 user 对象
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody
    public User showUser() {
        User user = new User();
        user.setAge(12);
        user.setName("lisi");
        return user;
    }

}
