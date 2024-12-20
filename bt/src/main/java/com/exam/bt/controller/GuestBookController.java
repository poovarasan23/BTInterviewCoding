package com.exam.bt.controller;

import com.exam.bt.entity.GuestBookEntry;
import com.exam.bt.service.BookService;
import com.exam.bt.util.helper.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

@Controller
public class GuestBookController {

    @Autowired
    BookService bookService;

    @GetMapping("/booking")
    public String addEntry(Model model) {
        model.addAttribute("guestBookEntry", new GuestBookEntry());
        return "add-entry";

    }

    @PostMapping("/new/entry/name")
    public String submitEntry(@RequestParam("name") String name, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
        GuestBookEntry guestBookEntry = new GuestBookEntry();
        guestBookEntry.setName(name);
        guestBookEntry.setUser((String) session.getAttribute("User"));
        guestBookEntry.setStatus("PENDING");
        model.addAttribute("entries", bookService.addEntry(guestBookEntry));
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Entry Successful"); //Todo: msg is not displaying
        return "guest-booking";
    }

    @PostMapping("/new/entry/image")
    public String submitEntry(@RequestParam("image") MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session, Model model) throws IOException {
        GuestBookEntry guestBookEntry = new GuestBookEntry();
        guestBookEntry.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        guestBookEntry.setUser((String) session.getAttribute("User"));
        guestBookEntry.setStatus("PENDING");
        model.addAttribute("entries", bookService.addEntry(guestBookEntry));
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Entry Successful"); //Todo: msg is not displaying
        return "guest-booking";
    }

}
