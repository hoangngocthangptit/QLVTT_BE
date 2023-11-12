package com.example.blogchipo.response;

import lombok.Data;
import org.hibernate.annotations.Nationalized;
@Data
public class UserDTO {
    private long userId;
    private String hoTen;
    private String maNV;
    private String email;
    private Long sdt;
    private boolean trangThai;
    private String role;
    private String maCN;
    private String chinhNhanh;
    private String password;

    public UserDTO(long userId, String hoTen, String maNV, String email, Long sdt, boolean trangThai, String role, String maCN, String chinhNhanh, String password) {
        this.userId = userId;
        this.hoTen = hoTen;
        this.maNV = maNV;
        this.email = email;
        this.sdt = sdt;
        this.trangThai = trangThai;
        this.role = role;
        this.maCN = maCN;
        this.chinhNhanh = chinhNhanh;
        this.password = password;
    }
}
