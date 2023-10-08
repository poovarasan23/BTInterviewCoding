package com.exam.bt;

import com.exam.bt.controller.AdminController;
import com.exam.bt.entity.GuestBookEntry;
import com.exam.bt.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private BookService bookService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdate() {
        GuestBookEntry guestBookEntry = new GuestBookEntry();
        String viewName = adminController.update(guestBookEntry, model);
        assertEquals("update-status", viewName);
    }

    @Test
    public void testUpdateStatus() throws IOException {
        GuestBookEntry guestBookEntry = new GuestBookEntry();
        MultipartFile imageFile = mock(MultipartFile.class);
        when(imageFile.getBytes()).thenReturn("YourImageData".getBytes());
        String viewName = adminController.updateStatus(guestBookEntry, imageFile, model);
        assertEquals("guest-booking-admin", viewName);
        verify(bookService).updateEntry(guestBookEntry);
    }

    @Test
    public void testDelete() {
        GuestBookEntry guestBookEntry = new GuestBookEntry();
        String viewName = adminController.delete(guestBookEntry, model);
        assertEquals("guest-booking-admin", viewName);
        verify(bookService).deleteEntry(guestBookEntry);
    }
}
