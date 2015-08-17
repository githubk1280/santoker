package com.santoker.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lefu on 15/8/14.
 * Description:
 */
@Controller
public class AdminController {

    @RequestMapping("admin")
    public ModelAndView index() {
        return new ModelAndView("views/index");
    }
}
