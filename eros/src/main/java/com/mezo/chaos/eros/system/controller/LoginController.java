package com.mezo.chaos.eros.system.controller;

import com.mezo.chaos.eros.system.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author mezo
 */
@RestController
@RequestMapping("user")
public class LoginController {

    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "";
    }

    @RequestMapping("/authLogin")
    public String authLogin(String username, String password) {
        return "";
    }

    @RequestMapping("/logout")
    public String logout(String token) {
        return "";
    }
}
