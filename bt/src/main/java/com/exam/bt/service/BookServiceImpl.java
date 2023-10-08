package com.exam.bt.service;

import com.exam.bt.dto.UserDTO;
import com.exam.bt.dto.UserSessionDTO;
import com.exam.bt.entity.GuestBookEntry;
import com.exam.bt.entity.User;
import com.exam.bt.repository.GuestBookEntryRepository;
import com.exam.bt.repository.UserRepository;
import com.exam.bt.util.enums.Role;
import com.exam.bt.util.helper.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    @Autowired
    GuestBookEntryRepository entryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public String addUser(UserDTO user) {
        String res;
        User u = userRepository.findByUserId(user.getUserName());
        if (u == null){
            User user1=new User(user);
            user1.setRole(Role.GUEST);
            userRepository.save(user1);
            res = Constants.USER_CREATED_SUCCESSFULLY;
        }else {
            res =Constants.USER_ALREADY_PRESENT;
        }
        return res;
    }

    @Override
    public UserSessionDTO validate(UserDTO dto) {
        UserSessionDTO sessionDTO;
        User user = userRepository.findByUserId(dto.getUserName());
        if (user != null) {
            sessionDTO = user.getPassword().equals(dto.getPassword()) ?
                    new UserSessionDTO(user.getUserName(),user.getRole(),Constants.LOGIN_SUCCESSFUL) : new UserSessionDTO(Constants.INCORRECT_PASSWORD);
        } else {
            sessionDTO = new UserSessionDTO(Constants.USER_NOT_AVAILABLE);
        }
        return sessionDTO;
    }

    @Override
    public List<GuestBookEntry> getEntriesByName(String name) {
        return entryRepository.findByUsers(name);
    }

    @Override
    public List<GuestBookEntry> getAllEntries() {
        return entryRepository.findAll();
    }

    @Override @Transactional
    public List<GuestBookEntry> addEntry(GuestBookEntry bookEntry) {
        entryRepository.save(bookEntry);
        return getEntriesByName(bookEntry.getUser());
    }

    @Override @Transactional
    public void updateEntry(GuestBookEntry bookEntry) {
        entryRepository.save(bookEntry);
    }

    @Override
    public void deleteEntry(GuestBookEntry bookEntry) {
        entryRepository.delete(bookEntry);
    }
}
