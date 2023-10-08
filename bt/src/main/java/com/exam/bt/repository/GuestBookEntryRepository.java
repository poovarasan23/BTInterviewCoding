package com.exam.bt.repository;

import com.exam.bt.entity.GuestBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestBookEntryRepository extends JpaRepository<GuestBookEntry, Long> {

//    @Query(value = """
//            SELECT * FROM GuestBookEntry WHERE user.userName = :userName
//            """,nativeQuery = true)
    List<GuestBookEntry> findByUsers(String userName);
}