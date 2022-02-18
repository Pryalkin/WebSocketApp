package com.example.messagingstompwebsocket.service;

import com.example.messagingstompwebsocket.entity.MessageRoom;
import com.example.messagingstompwebsocket.repo.MessageRoomRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class MessageRoomService {

    @Autowired
    private MessageRoomRepository messageRoomRepository;

    public void addRoom(Long recipientId, Long senderId){
        if (!(messageRoomRepository.findBySenderIdAndAndRecipientId(senderId, recipientId).isPresent())){
            MessageRoom messageRoom = new MessageRoom();
            messageRoom.setSenderId(senderId);
            messageRoom.setRecipientId(recipientId);
            messageRoomRepository.save(messageRoom);
        }
    }

    public MessageRoom getMessageRoom(Long senderId, Long recipientId){
        return messageRoomRepository.findBySenderIdAndAndRecipientId(senderId, recipientId).get();
    }

    public List<Long> getMessageRoomFriends(Long senderId){
        List<Long> messageRoomFriends = new ArrayList<>();
        for (MessageRoom room: messageRoomRepository.findBySenderId(senderId)) {
            messageRoomFriends.add(room.getRecipientId());
        }
        return messageRoomFriends;
    }

}
