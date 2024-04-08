package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "KhachHang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MaKhachHang")
    private  String maKhachHang;

    @Column(name = "TenKhachHang")
    private  String tenKhachHang;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "Email")
    private String email;

    @Column(name = "MatKhau")
    private String matKhau;


    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "AnhKhachHang")
    private String anhKhachHang;

}
