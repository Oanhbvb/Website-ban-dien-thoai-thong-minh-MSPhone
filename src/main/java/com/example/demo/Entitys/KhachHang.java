package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KhachHang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private BigInteger id;

    @Column(name = "TenKhachHang")
    private  String tenKhachHang;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "Email")
    private String email;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "IDDiaChi")
    private Integer idDiaChi;

    @Column(name = "TrangThai")
    private Integer trangThai;

    public KhachHang(String sdt) {
        this.sdt = sdt;
    }
}
