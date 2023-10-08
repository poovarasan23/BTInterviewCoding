package com.exam.bt;

import com.exam.bt.controller.LoginController;
import com.exam.bt.dto.UserDTO;
import com.exam.bt.dto.UserSessionDTO;
import com.exam.bt.service.BookService;
import com.exam.bt.util.enums.Role;
import com.exam.bt.util.helper.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private BookService bookService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUser() {
        UserDTO userDTO = new UserDTO();
        when(bookService.addUser(userDTO)).thenReturn("User added successfully");

        String viewName = loginController.addUser(userDTO, redirectAttributes);

        assertEquals("redirect:/", viewName);
        verify(redirectAttributes).addFlashAttribute(Constants.MESSAGE, "User added successfully");
    }

    @Test
    public void testLoginSuccessful() {
        UserDTO user = new UserDTO();
        UserSessionDTO userSessionDTO = new UserSessionDTO();
        userSessionDTO.setMsg(Constants.LOGIN_SUCCESSFUL);
        userSessionDTO.setRole(Role.GUEST);
        userSessionDTO.setUserName("user123");
        when(bookService.validate(user)).thenReturn(userSessionDTO);

        String viewName = loginController.login(user, model, redirectAttributes, session);

        assertEquals("guest-booking", viewName);
        verify(session).setAttribute("USer", "user123");
        verify(model).addAttribute("entries", bookService.getEntriesByName(user.getUserName()));
    }

    @Test
    public void testLoginAdmin() {
        UserDTO user = new UserDTO();
        UserSessionDTO userSessionDTO = new UserSessionDTO();
        userSessionDTO.setMsg(Constants.LOGIN_SUCCESSFUL);
        userSessionDTO.setRole(Role.ADMINISTRATOR);
        userSessionDTO.setUserName("admin123");
        when(bookService.validate(user)).thenReturn(userSessionDTO);

        String viewName = loginController.login(user, model, redirectAttributes, session);

        assertEquals("guest-booking-admin", viewName);
        verify(session).setAttribute("USer", "admin123");
        verify(model).addAttribute("entries", bookService.getAllEntries());
    }

    @Test
    public void testLoginIncorrectPassword() {
        UserDTO user = new UserDTO();
        UserSessionDTO userSessionDTO = new UserSessionDTO();
        userSessionDTO.setMsg(Constants.INCORRECT_PASSWORD);
        when(bookService.validate(user)).thenReturn(userSessionDTO);

        String viewName = loginController.login(user, model, redirectAttributes, session);

        assertEquals("redirect:/login", viewName);
        verify(redirectAttributes).addFlashAttribute(Constants.MESSAGE, Constants.INCORRECT_PASSWORD);
    }

    @Test
    public void testLoginOtherError() {
        UserDTO user = new UserDTO();
        UserSessionDTO userSessionDTO = new UserSessionDTO();
        userSessionDTO.setMsg("Some other error");
        when(bookService.validate(user)).thenReturn(userSessionDTO);

        String viewName = loginController.login(user, model, redirectAttributes, session);

        assertEquals("redirect:/", viewName);
        verify(redirectAttributes).addFlashAttribute(Constants.MESSAGE, "Some other error");
    }

    @Test
    public void testLogout() {
        String viewName = loginController.logout();

        assertEquals("redirect:/", viewName);
    }
}

