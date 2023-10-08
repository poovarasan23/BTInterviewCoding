package com.exam.bt;

import com.exam.bt.controller.GuestBookController;
import com.exam.bt.entity.GuestBookEntry;
import com.exam.bt.service.BookService;
import com.exam.bt.util.helper.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GuestBookControllerTest {

    @InjectMocks
    private GuestBookController guestBookController;

    @Mock
    private BookService bookService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddEntry() {
        String viewName = guestBookController.addEntry(model);
        assertEquals("add-entry", viewName);
    }

    @Test
    public void testSubmitEntryWithName() {
        String name = "John";
        GuestBookEntry expectedGuestBookEntry = new GuestBookEntry();
        expectedGuestBookEntry.setName(name);
        expectedGuestBookEntry.setUser("user123");
        expectedGuestBookEntry.setStatus("PENDING");

        when(session.getAttribute("User")).thenReturn("user123");
        when(bookService.addEntry(expectedGuestBookEntry)).thenReturn(Collections.emptyList());

        String viewName = guestBookController.submitEntry(name, redirectAttributes, session, model);

        assertEquals("guest-booking", viewName);
        verify(redirectAttributes).addFlashAttribute(Constants.MESSAGE, "Entry Successful");
    }


    @Test
    public void testSubmitEntryWithImage() throws IOException {
        byte[] imageBytes = "YourImageData".getBytes();
        when(multipartFile.getBytes()).thenReturn(imageBytes);
        when(session.getAttribute("User")).thenReturn("user123");
        when(bookService.addEntry(any(GuestBookEntry.class))).thenReturn(Collections.emptyList());

        String viewName = guestBookController.submitEntry(multipartFile, redirectAttributes, session, model);

        assertEquals("guest-booking", viewName);
        verify(bookService).addEntry(any(GuestBookEntry.class));
        verify(redirectAttributes).addFlashAttribute(Constants.MESSAGE, "Entry Successful");
    }
}
