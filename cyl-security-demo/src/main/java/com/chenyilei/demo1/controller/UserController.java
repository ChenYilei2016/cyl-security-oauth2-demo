package com.chenyilei.demo1.controller;

import com.chenyilei.demo1.dto.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/27- 16:16
 */
@RestController
@Api(value = "UserControllerUserControllerUserControllerUserController")
public class UserController {

    @GetMapping("/user/{id:\\d+}")
    @JsonView(User.UserSimpleView.class)
    @ApiOperation("users ! !")
    public List<User> users(@PathVariable String id, Date date){
        System.out.println("users:"+Thread.currentThread().getName());
        ArrayList<User> users = new ArrayList<>();
        User e = new User();
        e.setBirthday(date);
        e.setId("12");
        e.setUsername("n");
        e.setPassword("aaa");
        users.add(e);
        return users;
    }

    @GetMapping("/e")
    @JsonView(User.UserSimpleView.class)
    public List<User> exceptionM(){
        throw new MyException("nihao");
    }

    @PostMapping("/user")
    public Object postUser(@RequestBody @Valid User user
                        , BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            //.....
            //throw new RuntimeException("参数异常");
        }
        System.out.println(user.getBirthday());
        return user;
    }

    @GetMapping("/user/me")
    public Object hello(Authentication authentication,
                        @AuthenticationPrincipal UserDetails userDetails){

        return "user/me数据: \n"+authentication +"=======" + userDetails;
    }

}
