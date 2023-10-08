package com.exam.bt.service;

import com.exam.bt.dto.UserDTO;
import com.exam.bt.dto.UserSessionDTO;
import com.exam.bt.entity.GuestBookEntry;
import com.exam.bt.entity.User;

import java.util.List;

public interface BookService {

    public String addUser(UserDTO user);
    public UserSessionDTO validate(UserDTO user);
    public List<GuestBookEntry> getEntriesByName(String name);
    public List<GuestBookEntry> getAllEntries(); //TODO: include the name of booking person

    public List<GuestBookEntry> addEntry(GuestBookEntry bookEntry);
    public void updateEntry(GuestBookEntry bookEntry);
    public void deleteEntry(GuestBookEntry bookEntry);
}
