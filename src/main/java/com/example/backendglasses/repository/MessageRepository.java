package com.example.backendglasses.repository;

import com.example.backendglasses.model.Message;
import com.example.backendglasses.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query(value = "select * from messages where sender_id = :idAccount or receiver_id =  :idAccount\n" +
            "order by messages.timestamp ASC", nativeQuery = true)
    List<Message> getAllBySender(Long idAccount);
}
