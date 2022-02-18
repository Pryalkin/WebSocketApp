package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView home(Map<String, Object> model, HttpServletResponse response) throws IOException {
        if (checkAuthorization()){
            response.sendRedirect("/users");
            return new ModelAndView("users", model);
        } else
            return new ModelAndView("greeting", model);
    }

    @RequestMapping("/login")
    public ModelAndView login(Map<String, Object> model, HttpServletResponse response) throws IOException {
        if (checkAuthorization()){
            response.sendRedirect("/users");
            return new ModelAndView("users", model);
        } else
            return new ModelAndView ("sign_in", model);
    }

    @GetMapping("/sign_up")
    public ModelAndView getSingUp(Map<String, Object> model, HttpServletResponse response) throws IOException {
        if (checkAuthorization()){
            response.sendRedirect("/users");
            return new ModelAndView("users", model);
        } else {
            model.put("registration_result", "");
            return new ModelAndView ("sign_up", model);
        }
    }

    @PostMapping("/sign_up")
    public ModelAndView singUp(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Map<String,Object> model,
                               HttpServletResponse response) throws IOException {
        if (userService.checkAll(name, email, password)){
            model.put("registration_result", "Such an account already exists");
            return new ModelAndView("sign_up", model);
        }   else if (userService.checkEmail(email)){
            model.put("registration_result", "This email already exists");
            return new ModelAndView("sign_up", model);
        }
            else userService.add(name, email, password);
            response.sendRedirect("/login");
        return new ModelAndView("sign_in", model);
    }

    private boolean checkAuthorization() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return true;
        } else return false;
    }
}
