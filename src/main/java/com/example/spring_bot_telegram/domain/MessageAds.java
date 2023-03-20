package com.example.spring_bot_telegram.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;



@Getter
@Setter
@Entity(name = "message_ads_table")
public class MessageAds {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;

}
