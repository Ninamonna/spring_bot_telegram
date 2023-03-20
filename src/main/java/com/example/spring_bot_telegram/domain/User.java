package com.example.spring_bot_telegram.domain;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


import java.sql.Timestamp;

@Data
@Entity(name="users")
public class User {
    @Id
    private long chatId;
    private Boolean embedeJoke;

    private String phoneNumber;

    private Timestamp registeredAt;

    private String firstName;

    private String lastName;

    private String userName;

    private Double latitude;

    private Double longitude;

    private String bio;

    private String description;

    private String pinnedMessage;



}
