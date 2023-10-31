package com.example.blogchipo.service;

import com.example.blogchipo.entity.Users;
import org.springframework.stereotype.Service;


public interface UserService {
    Users login(Users information);
    boolean register(Users information);
}
