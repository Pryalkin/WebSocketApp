package com.example.messagingstompwebsocket.controller;


import com.example.messagingstompwebsocket.entity.MessageRoom;
import com.example.messagingstompwebsocket.entity.User;
import com.example.messagingstompwebsocket.service.MessageRoomService;
import com.example.messagingstompwebsocket.service.MessageService;
import com.example.messagingstompwebsocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRoomService messageRoomService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ModelAndView room(@AuthenticationPrincipal User user,
                             MessageRoom messageRoom,
                             Map<String, Object> model) throws Exception{


        model.put("senderId", messageRoom.getSenderId());
        model.put("recipientId", messageRoom.getRecipientId());
        model.put("messages", messageService.getMessages(messageRoom.getSenderId(), messageRoom.getRecipientId()));
        model.put("emailFiend", userService.getEmailFriend(messageRoom.getRecipientId()));
        model.put("email", user.getEmail());
        return new ModelAndView("friend_room", model);
    }


}

