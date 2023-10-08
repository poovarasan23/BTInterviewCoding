package com.exam.bt.controller;

import com.exam.bt.dto.SecurityUserDataBindDTO;
import com.exam.bt.dto.UserDTO;
import com.exam.bt.dto.UserSessionDTO;
import com.exam.bt.service.BookService;
import com.exam.bt.util.enums.Role;
import com.exam.bt.util.helper.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String getStart() {
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "signup";
    }

    @PostMapping("/signup/data")
    public String addUser(UserDTO user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, bookService.addUser(user));
        return "redirect:/";
    }


    @GetMapping("/login")
    public String showLogInPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @GetMapping("/login/data")
    public String login(UserDTO user, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        UserSessionDTO res = bookService.validate(user);
        if (res.getMsg().equals(Constants.LOGIN_SUCCESSFUL)) {
            new SecurityUserDataBindDTO(res.getRole().toString());
            session.setAttribute("USer",res.getUserName());
            if (res.getRole().equals(Role.ADMINISTRATOR)) {
                model.addAttribute("entries", bookService.getAllEntries());
                return "guest-booking-admin";
            } else {
                model.addAttribute("entries", bookService.getEntriesByName(user.getUserName()));
                return "guest-booking";
            }
        } else if (res.getMsg().equals(Constants.INCORRECT_PASSWORD)) {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, res.getMsg());
            return "redirect:/login";

        } else {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, res.getMsg());
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}
