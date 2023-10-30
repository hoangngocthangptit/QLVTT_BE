package com.example.blogchipo.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Nationalized
    private String name;
    private String email;
    private String password;
    private Long mobileNumber;
    private LocalDateTime createdDate;
    private boolean isVerified;
    private String role;
}
