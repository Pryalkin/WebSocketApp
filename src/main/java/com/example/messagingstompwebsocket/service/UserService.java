package com.example.messagingstompwebsocket.service;

import com.example.messagingstompwebsocket.entity.User;
import com.example.messagingstompwebsocket.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void add(String name, String email, String password){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
    }

    public boolean checkAll(String name, String email, String password){
        return userRepository.findByNameAndEmailAndPassword(name, email, password).isPresent();
    }

    public boolean checkEmail(String email){
        if (userRepository.findByEmail(email) == null) return false;
        else return true;
    }

    public List<User> getAll(User user){
        List <User> getUser = userRepository.findAll();
        getUser.remove(user);
        return getUser;
    }

    public List<User> getEmailFriend(List<Long> recipientIdList){
        return userRepository.findAllById(recipientIdList);
    }

    public String getEmailFriend(Long recipientId){
        return userRepository.findById(recipientId).get().getEmail();
    }

    public Long getUserId (String email){
        return userRepository.findByEmail(email).getId();
    }

}
