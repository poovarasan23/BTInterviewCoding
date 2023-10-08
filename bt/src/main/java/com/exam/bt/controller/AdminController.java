package com.exam.bt.controller;

import com.exam.bt.entity.GuestBookEntry;
import com.exam.bt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class AdminController {

    @Autowired
    BookService bookService;

    @GetMapping("/update")
    public String update(GuestBookEntry guestBookEntry, Model model) {
        model.addAttribute("guestBookEntry", guestBookEntry);
        return "update-status";
    }

    @PostMapping("/update/data")
    public String updateStatus(@ModelAttribute GuestBookEntry guestBookEntry, @RequestParam("images") MultipartFile imageFile, Model model) throws IOException {
        guestBookEntry.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));
        bookService.updateEntry(guestBookEntry);
        model.addAttribute("entries", bookService.getAllEntries());
        return "guest-booking-admin";
    }

    @PostMapping("/delete")
    public String delete(GuestBookEntry guestBookEntry, Model model) {
        bookService.deleteEntry(guestBookEntry);
        model.addAttribute("entries", bookService.getAllEntries());
        return "guest-booking-admin";
    }
}
