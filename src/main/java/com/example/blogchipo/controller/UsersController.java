package com.example.blogchipo.controller;

import com.example.blogchipo.entity.ChiNhanhEntity;
import com.example.blogchipo.entity.Users;
import com.example.blogchipo.repository.ChiNhanhRepository;
import com.example.blogchipo.repository.UsersRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.response.UserDTO;
import com.example.blogchipo.response.UsersDetailRes;
import com.example.blogchipo.service.UserService;
import com.example.blogchipo.until.JwtGenerator;
import com.example.blogchipo.until.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private ChiNhanhRepository chiNhanhRepository;
    @Autowired
    private BCryptPasswordEncoder encryption;
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
        if (users != null&& users.isTrangThai()==true) {
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
        if(user.isTrangThai()==false) {
            user.setTrangThai(true);
        }
        else {
            user.setTrangThai(false);
        }
        repository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("set isVerified", 200, user));
    }
    @GetMapping("User/getAll")
    public ResponseEntity<Response> getAll() {
        List<Users> users = (List<Users>) repository.findAll();
        List<UserDTO> userDTOList = users.stream().map(info -> new UserDTO(info.getUserId(),info.getHoTen(), info.getMaNV(), info.getEmail(),info.getSdt(),info.isTrangThai(), info.getRole(),
                info.getMaCN().getMaCN(),info.getMaCN().getChiNhanh(), info.getPassword())).collect(Collectors.toList());;
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("set isVerified", 200, userDTOList));
    }

    @GetMapping("DetailUser/{userId}")
    public ResponseEntity<Response> detailUser(@PathVariable long userId) {
        Users user = repository.findById(userId).get();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("detail", 200, user));
    }
    @PutMapping("user/edit/{userId}")
    public ResponseEntity<Response> editUser(@RequestBody UserDTO info, @PathVariable long userId) {
        Users user = repository.findById(userId).get();
        user.setRole(info.getRole());
        user.setTrangThai(info.isTrangThai());
        user.setHoTen(info.getHoTen());
        user.setSdt(info.getSdt());
        ChiNhanhEntity cn = chiNhanhRepository.findByMaCN(info.getMaCN());
        user.setMaCN(cn);
        if(info.getPassword().length() <15 && !info.getPassword().isEmpty() ){
            String epassword = encryption.encode(info.getPassword());
            // setting the some extra information and encrypting the password
            user.setPassword(epassword);
        }
        repository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("detail", 200, user));
    }

}
