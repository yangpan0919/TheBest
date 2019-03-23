package com.best.zcdn.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by EDZ on 2018/10/26.
 */
@Controller
public class ModelView {


//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
//        return new ModelAndView("login.html");
//    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index.html");
    }
}
