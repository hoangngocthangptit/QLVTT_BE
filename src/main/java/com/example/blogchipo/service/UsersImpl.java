package com.example.blogchipo.service;

import com.example.blogchipo.entity.Users;
import com.example.blogchipo.repository.UsersRepository;
import com.example.blogchipo.until.JwtGenerator;
import com.example.blogchipo.until.UserException;
import org.springframework.beans.factory.annotation.Autowired;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersImpl implements UserService {
    @Autowired
    private UsersRepository repository;
    @Autowired
    private BCryptPasswordEncoder   encryption;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtGenerator generate;
    //    @Autowired
//    private EmailProviderService em;
    private Users users = new Users();
    @Override
    public Users login(Users information) {
        Users user = repository.findByEmail(information.getEmail());
        if (user != null) {
//            String userRole = information.getRole();
//            String fetchRole = user.getRole();
            if ((user.isTrangThai() == true)) {
                    if (encryption.matches(information.getPassword(), user.getPassword())) {
                    System.out.println(generate.jwtToken(user.getUserId()));
                    return user;
                } else {
                    throw new UserException("Invalid password");
                }
            } else {
                throw new UserException("Invalid verified");
            }
        }
        else {
            throw new UserException("User Not present enter valid your email id");
        }

    }

    @Override
    public boolean register(Users information) {
        Users user = repository.findByEmail(information.getEmail());
        if (user == null) {
            users = modelMapper.map(information, Users.class);
            String epassword = encryption.encode(information.getPassword());
            // setting the some extra information and encrypting the password
            users.setPassword(epassword);
            System.out.println("password is" + epassword);
            users.setTrangThai(true);
            // calling the save method
            users.setRole("admin");
            users = repository.save(users);
            return true;
        } else {
            throw new UserException("user already exist with the same mail id");
        }
    }

}
