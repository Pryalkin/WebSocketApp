package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.entity.User;
import com.example.messagingstompwebsocket.service.MessageRoomService;
import com.example.messagingstompwebsocket.service.MessageService;
import com.example.messagingstompwebsocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageRoomService messageRoomService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/users")
    public ModelAndView getUsers(@AuthenticationPrincipal User user,
                                 @RequestParam(required = false) String email,
                                 Map<String, Object> model){
        if (email != null){
            userService.getUserId(email);
            messageRoomService.addRoom(userService.getUserId(email), user.getId());
        }
        model.put("senderId", user.getId());
        model.put("email", user.getEmail());
        model.put("emailFriend", userService.getEmailFriend(messageRoomService.getMessageRoomFriends(user.getId())));
        return new ModelAndView("page", model);
    }

    @GetMapping("/users/{senderId}/{recipientId}")
    public ModelAndView getRoom(@AuthenticationPrincipal User user,
                                @PathVariable Long recipientId,
                                @PathVariable Long senderId,
                                @RequestParam (required = false) String message,
                                @RequestParam (required = false) String edit,
                                @RequestParam (required = false) String topic,
                                @RequestParam (required = false) Long idMes,
                                Map<String, Object> model){
        if (idMes != null){
            messageService.updateMessage(idMes, edit);
        }
        messageRoomService.addRoom(senderId, recipientId);
        if (message != null) {
            messageService.addMessage(recipientId, senderId, message, topic);
        }
        model.put("senderId", senderId);
        model.put("recipientId", recipientId);
        model.put("messages", messageService.getMessages(senderId, recipientId));
        model.put("emailFiend", userService.getEmailFriend(recipientId));
        model.put("email", user.getEmail());
        return new ModelAndView("friend_room", model);
    }

    @GetMapping("/index")
    public ModelAndView getUsers(Map<String, Object> model){

        return new ModelAndView("index", model);
    }


}

