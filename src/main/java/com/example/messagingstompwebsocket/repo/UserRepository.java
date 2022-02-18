package com.example.messagingstompwebsocket.repo;


import com.example.messagingstompwebsocket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    public Optional<User> findByNameAndEmailAndPassword(String name, String email, String password);
//    public Optional<User> findByEmail(String email);
}
