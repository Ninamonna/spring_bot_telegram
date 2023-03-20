package com.example.spring_bot_telegram.repos;

import com.example.spring_bot_telegram.domain.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepo extends CrudRepository<User, Long> {

}
