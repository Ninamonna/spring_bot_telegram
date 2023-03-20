package com.example.spring_bot_telegram.repos;

import com.example.spring_bot_telegram.domain.Joke;
import org.springframework.data.repository.CrudRepository;

public interface JokeRepo extends CrudRepository<Joke, Long> {
}
