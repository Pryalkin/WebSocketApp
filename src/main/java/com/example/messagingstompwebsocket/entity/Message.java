package com.example.messagingstompwebsocket.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Date date;
    private String topic;
    @OneToOne
    private MessageRoom messageRoom;


}
