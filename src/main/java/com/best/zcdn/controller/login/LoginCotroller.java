package com.best.zcdn.controller.login;

import com.best.zcdn.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by EDZ on 2018/10/25.
 */
@Controller
@RequestMapping("/user")
public class LoginCotroller {


    @PostMapping(value = "/login")
    public  String  login(HttpServletRequest request, @RequestBody User user){

        System.out.println(user.getUserName()+":"+user.getPassWord());


        return  "/index";
    }
}
