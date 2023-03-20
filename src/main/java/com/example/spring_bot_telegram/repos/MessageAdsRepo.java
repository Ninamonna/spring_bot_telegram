package com.example.spring_bot_telegram.repos;

import com.example.spring_bot_telegram.domain.MessageAds;
import org.springframework.data.repository.CrudRepository;

public interface MessageAdsRepo extends CrudRepository<MessageAds, Long> {
}
