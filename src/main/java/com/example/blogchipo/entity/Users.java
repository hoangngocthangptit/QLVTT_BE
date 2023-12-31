package com.example.blogchipo.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.Set;

@Entity
@Data
@Table(name = "NhanVien")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Nationalized
    private String hoTen;
    @Nationalized
    private String maNV;
    private String email;
    private String password;
    private Long sdt;
    private boolean trangThai;
    private String role;
    @ManyToOne
    @JoinColumn(name = "maCN", referencedColumnName = "maCN", nullable = false)
    private ChiNhanhEntity maCN;
}
