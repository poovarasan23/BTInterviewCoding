package com.exam.bt;

import com.exam.bt.dto.UserDTO;
import com.exam.bt.dto.UserSessionDTO;
import com.exam.bt.entity.GuestBookEntry;
import com.exam.bt.entity.User;
import com.exam.bt.repository.GuestBookEntryRepository;
import com.exam.bt.repository.UserRepository;
import com.exam.bt.util.enums.Role;
import com.exam.bt.util.helper.Constants;
import com.exam.bt.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private GuestBookEntryRepository entryRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUserSuccess() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("john");
        when(userRepository.findByUserId("john")).thenReturn(null);

        String result = bookService.addUser(userDTO);

        assertEquals(Constants.USER_CREATED_SUCCESSFULLY, result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testAddUserAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("john");
        when(userRepository.findByUserId("john")).thenReturn(new User());

        String result = bookService.addUser(userDTO);

        assertEquals(Constants.USER_ALREADY_PRESENT, result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testValidateSuccessfulLogin() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("john");
        userDTO.setPassword("password");

        User user = new User();
        user.setUserName("john");
        user.setPassword("password");
        user.setRole(Role.GUEST);

        when(userRepository.findByUserId("john")).thenReturn(user);

        UserSessionDTO result = bookService.validate(userDTO);

        assertEquals(Constants.LOGIN_SUCCESSFUL, result.getMsg());
        assertEquals(Role.GUEST, result.getRole());
    }

    @Test
    public void testValidateIncorrectPassword() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("john");
        userDTO.setPassword("wrong_password");

        User user = new User();
        user.setUserName("john");
        user.setPassword("correct_password");
        user.setRole(Role.GUEST);

        when(userRepository.findByUserId("john")).thenReturn(user);

        UserSessionDTO result = bookService.validate(userDTO);

        assertEquals(Constants.INCORRECT_PASSWORD, result.getMsg());
        assertNull(result.getRole());
    }

    @Test
    public void testValidateUserNotAvailable() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("nonexistent_user");
        userDTO.setPassword("password");

        when(userRepository.findByUserId("nonexistent_user")).thenReturn(null);

        UserSessionDTO result = bookService.validate(userDTO);

        assertEquals(Constants.USER_NOT_AVAILABLE, result.getMsg());
        assertNull(result.getRole());
    }

    @Test
    public void testGetEntriesByName() {
        String userName = "john";
        List<GuestBookEntry> expectedEntries = new ArrayList<>();
        when(entryRepository.findByUsers(userName)).thenReturn(expectedEntries);

        List<GuestBookEntry> result = bookService.getEntriesByName(userName);

        assertSame(expectedEntries, result);
    }

    @Test
    public void testGetAllEntries() {
        List<GuestBookEntry> expectedEntries = new ArrayList<>();
        when(entryRepository.findAll()).thenReturn(expectedEntries);

        List<GuestBookEntry> result = bookService.getAllEntries();

        assertSame(expectedEntries, result);
    }

    @Test
    public void testAddEntry() {
        GuestBookEntry bookEntry = new GuestBookEntry();
        when(entryRepository.save(any())).thenReturn(bookEntry);

        List<GuestBookEntry> result = bookService.addEntry(bookEntry);

        verify(entryRepository).save(bookEntry);
    }

    @Test
    public void testUpdateEntry() {
        GuestBookEntry bookEntry = new GuestBookEntry();
        when(entryRepository.save(bookEntry)).thenReturn(bookEntry);

        bookService.updateEntry(bookEntry);

        verify(entryRepository).save(bookEntry);
    }

    @Test
    public void testDeleteEntry() {
        GuestBookEntry bookEntry = new GuestBookEntry();

        bookService.deleteEntry(bookEntry);

        verify(entryRepository).delete(bookEntry);
    }

    @Test
    public void testAddUserUserAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("john");
        when(userRepository.findByUserId("john")).thenReturn(new User());

        String result = bookService.addUser(userDTO);

        assertEquals(Constants.USER_ALREADY_PRESENT, result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testValidateIncorrect_Password() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("john");
        userDTO.setPassword("wrong_password");

        User user = new User();
        user.setUserName("john");
        user.setPassword("correct_password");
        user.setRole(Role.GUEST);

        when(userRepository.findByUserId("john")).thenReturn(user);

        UserSessionDTO result = bookService.validate(userDTO);

        assertEquals(Constants.INCORRECT_PASSWORD, result.getMsg());
        assertNull(result.getRole());
    }

    @Test
    public void testValidateUserNot_Available() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("nonexistent_user");
        userDTO.setPassword("password");

        when(userRepository.findByUserId("nonexistent_user")).thenReturn(null);

        UserSessionDTO result = bookService.validate(userDTO);

        assertEquals(Constants.USER_NOT_AVAILABLE, result.getMsg());
        assertNull(result.getRole());
    }

    @Test
    public void testAddEntryFailure() {
        GuestBookEntry bookEntry = new GuestBookEntry();
        when(entryRepository.save(bookEntry)).thenThrow(new RuntimeException("Failed to save entry"));

        assertThrows(RuntimeException.class, () -> bookService.addEntry(bookEntry));
        verify(entryRepository).save(bookEntry);
    }

    @Test
    public void testUpdateEntryFailure() {
        GuestBookEntry bookEntry = new GuestBookEntry();
        when(entryRepository.save(bookEntry)).thenThrow(new RuntimeException("Failed to update entry"));

        assertThrows(RuntimeException.class, () -> bookService.updateEntry(bookEntry));
        verify(entryRepository).save(bookEntry);
    }

    @Test
    public void testDeleteEntryFailure() {
        GuestBookEntry bookEntry = new GuestBookEntry();
        doThrow(new RuntimeException("Failed to delete entry")).when(entryRepository).delete(bookEntry);

        assertThrows(RuntimeException.class, () -> bookService.deleteEntry(bookEntry));
        verify(entryRepository).delete(bookEntry);
    }

}

