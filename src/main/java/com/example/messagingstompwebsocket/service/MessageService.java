package com.example.messagingstompwebsocket.service;

import com.example.messagingstompwebsocket.entity.Message;
import com.example.messagingstompwebsocket.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageRoomService messageRoomService;

    public void addMessage(Long recipientId, Long senderId, String message, String topic){
        Message messageFriend = new Message();
        messageFriend.setMessage(message);
        Date date = new Date();
        messageFriend.setDate(date);
        messageFriend.setTopic(topic);
        messageFriend.setMessageRoom(messageRoomService.getMessageRoom(senderId, recipientId));
        messageRepository.save(messageFriend);
    }

    public List<Message> getMessages(Long senderId, Long recipientId){
        List message = messageRepository.findByMessageRoom(messageRoomService.getMessageRoom(senderId, recipientId));
        message.addAll(messageRepository.findByMessageRoom(messageRoomService.getMessageRoom(recipientId, senderId)));
        message.sort(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if (o1.getDate().equals(o2.getDate())) return 0;
                else if (o1.getDate().after(o2.getDate())) return 1;
                else return -1;
            }
        });
        return message;
    }

    public void updateMessage(Long id, String messageUpdate){
        Message message = messageRepository.findById(id).get();
        message.setMessage(messageUpdate + "(ред.)");
        messageRepository.save(message);
    }

}
