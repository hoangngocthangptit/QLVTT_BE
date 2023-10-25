package com.example.blogchipo.controller;

import com.example.blogchipo.entity.Users;
import com.example.blogchipo.repository.UsersRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.response.UsersDetailRes;
import com.example.blogchipo.service.UserService;
import com.example.blogchipo.until.JwtGenerator;
import com.example.blogchipo.until.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
//@RequestMapping("/user")
@RestController
public class UsersController {
    @Autowired
    private UsersRepository repository;
    @Autowired
    private UserService userServices;
    @Autowired
    private JwtGenerator generate;
    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<Response> registration(@RequestBody Users information) {
        System.out.println("user info" + information.toString());
        boolean result = userServices.register(information);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Response("registration successfull", 200, information));
        } else {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(new Response("User Already Exist", 400, information));
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<UsersDetailRes> login(@RequestBody Users information) {
        Users users = userServices.login(information);
        if (users != null&& users.isVerified()==true) {
            String token = generate.jwtToken(users.getUserId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull", information.getEmail())
                    .body(new UsersDetailRes(token, 200, users));
        } else {
            throw new UserException(" Invalide credentials");
        }
    }
    @GetMapping("DeleteUser/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable long userId) {
        Optional<Users> user = repository.findById(userId);
        repository.deleteById(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("delete", 200, user));
    }
    @GetMapping("isVerified/{userId}")
    public ResponseEntity<Response> activeUser(@PathVariable long userId) {
        Users user = repository.findById(userId).get();
        if(user.isVerified()==false) {
            user.setVerified(true);
        }
        else {
            user.setVerified(false);
        }
        repository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("set isVerified", 200, user));
    }
    @GetMapping("User/getAll")
    public ResponseEntity<Response> getAll() {
        List<Users> users = (List<Users>) repository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("set isVerified", 200, users));
    }

    @GetMapping("DetailUser/{userId}")
    public ResponseEntity<Response> detailUser(@PathVariable long userId) {
        Users user = repository.findById(userId).get();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("detail", 200, user));
    }

}
