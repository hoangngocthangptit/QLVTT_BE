package com.example.blogchipo.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Entity
@Data
@Table(name = "NhanVien")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Nationalized
    private String hoTen;
    private String email;
    private String password;
    private Long sdt;
    private boolean trangThai;
    private String role;

}
