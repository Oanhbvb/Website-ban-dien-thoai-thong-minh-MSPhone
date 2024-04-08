package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DiaChi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DiaChiCuThe")
    private  String diaChiCuThe;

    @Column(name = "Phuong")
    private String phuong;

    @Column(name = "Quan")
    private String quan;

    @Column(name = "Tinh")
    private String tinh;

    @Column(name = "IDKhachHang")
    private Integer idKhachHang;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
