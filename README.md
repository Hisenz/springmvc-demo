# spring mvc demo

## 1. spring web 集成

### 1.1 应用上下文获取方式

Spring 应用上下文对象是通过new ClassPathXmlApplicationContext(Spring 配置文件) 方式获取的，但是每次从容器中获取Bean都需要编写 `new ClassPathXmlApplication(Spring 配置文件)`,导致配置文件多次加载，上下文对象多次创建。

在Web项目中，通过监听器，在Web应用启动时加载Spring配置文件，创建Spring上下文对象，存储到servlet上下文对象中，即可在任意位置从域中获取spring应用上下文对象

spring 提供了一个监听器ContextLoaderListener就是对上述功能的对象，该监听器内部加载Spring配置文件，创建应用上下文对象，并存储到ServletContext域中，提供了一个客户端工具，WebApplicationContextUtils供使用者获得应用上下文对象。

### 1.2 具体配置

- Spring集成Web坐标

    ```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-web</artifactId>
        <version>5.0.5.RELEASE</version>
    </dependency>
    ```

- 配置ContextLoaderListener监听器

    ```xml
    <!--  全局参数  -->
    <context-param>    
        <param-name>contextConfigLocation</param-name>    
        <param-value>classpath:applicationContext.xml</param-value></context-param>
    
    <!--  spring 监听器  -->
    <listener>    
        <listener-class>
    		org.springframework.web.context.ContextLoaderListener
        </listener-class>
    </listener>
    ```

- 获取上下文对象

    ```java
    ApplicationContext app = WebApplicationUtils.getWebApplicationContext(servletContext);
    Object obj = app.getBean("id");
    ```



## 2. Spring MVC

### 2.1 概述

Spring MVC 是一种基于 Java 的实现 MVC 设计模型的请求驱动类型的轻量级框架， 基于 SpringFramework 的后续产品，已融合在 Spring Web Flow 中。

### 2.2 Spring MVC组件与执行流程

![](img/SpringMVCexecutionflow.jpg)

1. 用户发送请求至前端控制器 DispatcherServlet
2. DispatcherServlet收到请求调用HandlerMapping处理器映射器
3. 处理器映射器找到具体的处理器（可以根据xml配置，注解进行查找），生成处理器对象以及处理器拦截器，一并返回给DispatcherServlet
4. DispatcherServlet 调用HandlerAdapter处理器适配器
5. HandlerAdapter经过适配调用具体的处理器(Controller(后端控制器))
6. Controller执行完成返回ModelAndView
7. HandlerAdapter将Controller执行结果ModelAndView返回给DispatcherServlet
8. DispatcherServlet将ModelAndView传给ViewReslover视图解析器
9. ViewReslover解析后返回具体View
10. DispatcherServket根据View进行视图渲染
11. DispatcherServlet响应用户

###  2.3 Spring MVC 注解

| 注解            | 作用                                        | 位置                                                         | 属性                                                         |
| --------------- | ------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| @RequestMapping | 用于建立请求URL和处理请求方法之间的对应关系 | 类上：请求URL的第一级访问目录，如果无，则默认为应用根目录。<br />方法上：请求URL的第二级访问目录，与类上使用的`@ResquestMapping`标注的一级目录一起组成访问虚拟路径 | value：用于指定请求的URL，它和path属性的作用是一致的<br />method：用于指定请求的方式<br />param：用于指定限制请求参数的条件，它支持简单的表达式，要求请i去参数的key和value必须配置的一模一样 |

- 引入命名空间和约束

    命名空间

    ```
    xmlns:mvc="http://www.springframework.org/schema/mvc"
        xmlns:context="http://www.springframework.org/schema/context"
    ```

    约束地址

    ```http
    http://www.springframework.org/schema/mvc
    http://www.springframework.org/schema/mvc/spring-mvc.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd
    ```

- 组件扫描

    Spring MVC 基于 Spring，默认组件配置都是`DispatcherServlet.properties`配置文件中配置的，该配置文件地址 `org/springframework/web/servlet/DispatcherServlet.properties`配置了默认的视图解析器，

    ```properties
    org.springframework.web.servlet.ViewResolver=org.springframework.web.servlet.view.InternalResourceViewResolver
    ```

    其默认配置如下

    ```ini
    REDIRECT_URL_PREFIX = "redirect:"	--重定向前缀
    FORWARD_URL_PREFIX = "forward:"		--转发前缀（默认值）
    prefix = "";	--视图名称前缀
    suffix = "";	--视图名称后缀
    ```

- 视图解析器

    通过属性注入方式修改视图的前后缀

    ```xml
    <!--配置内部资源视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    ```

### 2.4 请求与响应

1. 响应方式

    - 页面跳转

        直接返回字符串，由 `ModelAndView` 对象返回

    - 回写数据

        直接返回字符串，返回对象或集合

2. 页面跳转

    - 返回字符串形式

        直接返回字符串，会将返回的字符串与视图解析器的前后缀拼接后跳转

        ```java
        /**
          * 直接跳转
          *
          * @return
          */
        @RequestMapping("/hello")
        public String hello() {
            return "index.jsp";
        }
        ```

        

    - 返回`ModelAndView`形式

        Model（模型）：封装数据

        View（视图）：显示数据

        方式一

        ```java
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
        ```

        方式二

        ```java
        @RequestMapping("/modelAndView1")
        public ModelAndView modelAndViewMethod2(ModelAndView modelAndView) {
            modelAndView.addObject("message", "method2");
            modelAndView.setViewName("target");
            return modelAndView;
        }
        ```

        方式三

        ```java
        @RequestMapping("/modelAndView1")
        public String modelAndViewMethod3(Model model) {
            model.addAttribute("message", "method3");
            return "target";
        }
        ```

3. 回写数据

    在方法上添加 `@ResponseBody

    - 直接回写字符串

        ```java
        @RequestMapping("/direct")
        @ResponseBody
        public String direct() {
            return "direct write back data";
        }
        
        ```

    - 返回 json 数据

        手写 json 字符串返回

        ```java
        @RequestMapping("/jsonString1")
        @ResponseBody
        public String backJson() {
            return "{\"type\":\"json\",\"message\":\"content\"}";
        }
        ```

        使用 Jackson 工具将对象转化为 json

        ```java
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
        ```

        配置Spring MVC对对象和集合进行 json 字符串的转换回写，为处理器适配器配置消息转换参数，指定使用Jackson进行对象或集合的转换，因此需要在 Spring MVC 配置文件中进行如下配置:

        ```xml
        <!--  配置 Jackson 转换工具  -->
        <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
            <property name="messageConverters">
                <list>
                    <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
                </list>
            </property>
        </bean>
        ```

        直接返回对象

        ```java
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
        ```

4. 获取请求参数

    Controller 中的业务方法的参数名称要与请求参数的 name 一致，参数值会自动匹配，并且能自动进行类型转换

    - 获取基本类型参数

        ```java
        /**
             * 获取基本数据类型参数
             *  @param username
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
        ```

    - 获取 POJO 类型参数

        ```java
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
        ```

    - 获取数组类型参数

        ```java
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
        
        ```

    - 获取集合类型参数

        方式一：

        ```java
        /**
             * 集合类型参数
             * 需将集合参数包装到一个POJO中
             * @param vo
             */
            @RequestMapping("/list")
            @ResponseBody
            public VO listParam(VO vo){
                return vo;
            }
        ```

        方式二：

        ```java
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
        ```

    - 静态资源访问开启

        由于在`web.xml`中配置的 Spring MVC 前端控制器 `DispatcherServlet` 的 `url-pattern `配置的是 `/`，代表的是对所有的资源都进行过滤操作，当有静态资源需要加载时，比如 js、css等，需要在配置文件中配置放行，否则无法被加载到页面，有两种配置方式

        - 配置一

            ```xml
            <mvc:resources mapping="/img/**" location="/img/"/>
            <mvc:resources mapping="/js/**" location="/js/"/>
            ```

        - 配置二

            ```xml
            <mvc:default-servlet-handler/>
            ```

    - 乱码处理

        当POST请求时会出现乱码，可以设置一个过滤器来进行编码的过滤

        ```xml
        <!--  配置全局过滤器 filter  -->
        <filter>
            <filter-name>CharacterEncodingFilter</filter-name>
            <filter-class>
            org.springframework.web.filter.CharacterEncodingFilter
            </filter-class>
            <init-param>
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>CharacterEncodingFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
        ```

    - 参数绑定注解

        当请求的参数名称与Controller的业务方法参数名称不一致时，就需要通过`@RequestParam`注解显示的绑定

        ```java
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
        ```
        
    - restful 风格参数获取
    
        ```java
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
        
        ```
    
    - 获取servlet api
    
        ```java
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
        ```
    
    - 获取请求头参数
    
        ```java
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
        ```
    
    - 获取cookie
    
        ```java
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
        ```
    
    - 文件上传
    
        文件上传客户端的表单要满足以下条件
    
        - 表单项 type="file"
    
        - 提交方式：POST
    
        - 表单的enctype属性时多部份表单形式，即：`enctype=“multipart/form-data”`
    
            ```html
            <form action="/request/upload" method="post" enctype="multipart/form-data">
                <input type="text" name="username"/>
                <input type="file" name="uploadFile"/>
                <input type="submit" value="提交">
            </form>
            ```
    
        文件上传原理
    
        - 当表单修改为多部份表单时，request.getParameter() 将失效
        - `ectype=“application/x-www-form-urlencode”`时，form表单的正文格式为：key=value&key=value&key=value
        - 当form表单的enctype取值为Multipart/form-data时，请求正文内容就变成多部份形式
    
        ![](img/form.jpg)
    
        文件上传
    
        - 添加依赖
    
            ```xml
            <dependency>
                <groupId>commons-fileupload</groupId>
                <artifactId>commons-fileupload</artifactId>
                <version>1.3.1</version>
            </dependency>
            <dependency>
                <groupId>commons-io</groupId>
                <artifactId>commons-io</artifactId>
                <version>2.3</version>
            </dependency>
            ```
    
            
    
        - 配置文件上传解析器
    
            ```xml
            <!--  配置文件上传解析器  -->
            <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
                <property name="defaultEncoding" value="UTF-8"/>
                <property name="maxUploadSize" value="50000"/>
            </bean>
            ```
    
        - 服务端接受
    
            ```java
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
            ```



### 2.5 Spring MVC 拦截器

Spring MVC 拦截器类似于 servlet 中的 过滤器 Filter，用于对处理器进行预处理和后处理。将拦截器按一定顺序联结成一条链，这条链称为拦截器链（interceptionChain），在访问被拦截的方法或字段时，拦截器链中的拦截器就会按其之前的定义顺序调用，是AOP思想的具体实现

1. interceptor 和 filter 区别

    | 区别     | 过滤器                                                       | 拦截器                                                       |
    | -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
    | 使用范围 | 是servlet规范中的一部分，任何Java Web 工程都可以使用         | Spring MVC框架的一部分，只有在 Spring MVC框架的工程才能使用  |
    | 拦截范围 | 在 `url-pattern`中配置目标url后，就可以对对应的url资源进行拦截 | 只会拦截访问的控制器方法，如果访问的是jsp、html、css、image或者js是不会进行拦截的 |

2. HandlerInterceptor 接口

    ```java
    package org.springframework.web.servlet;
    
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    
    import org.springframework.lang.Nullable;
    import org.springframework.web.method.HandlerMethod;
    
    
    public interface HandlerInterceptor {
    	/**
    	 * 方法在请求处理之前执行，当 返回 false 时，表示请求结束，后续的
    	 * Interceptor 和 Controller 都不会再执行，当返回 true 时就
    	 * 会继续调用下一个Interceptor 的 preHandler 方法
     	 */
    	default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    			throws Exception {
    
    		return true;
    	}
    
    	/**
    	 * 该方法在当前请求进行处理之后被调用，前提是preHandler方法的返
    	 * 回值为true时才能被调用，且它会在DispatcherServlert进行视图
    	 * 返回渲染之前被调用，即可以在方法中队Controller处理之后的
    	 * ModelAndView 对象进行操作
    	 */
    	default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    			@Nullable ModelAndView modelAndView) throws Exception {
    	}
    
    	/**
    	 * 该方法在整个请求结束之后，也就是在DispatcherServlet渲染了
    	 * 对应的视图之后执行，前提是preHandler方法的返回值为true
    	 */
    	default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
    			@Nullable Exception ex) throws Exception {
    	}
    
    }
    
    ```

3. 配置拦截器

    