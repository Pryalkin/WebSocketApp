package com.example.messagingstompwebsocket.repo;

import com.example.messagingstompwebsocket.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {

    Optional<MessageRoom> findBySenderIdAndAndRecipientId(Long senderId, Long recipientId);
    List<MessageRoom> findBySenderId(Long senderId);
}
