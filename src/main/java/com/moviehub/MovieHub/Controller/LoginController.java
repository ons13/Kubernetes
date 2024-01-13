package com.moviehub.MovieHub.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String dashboard() {
        return "pages/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
