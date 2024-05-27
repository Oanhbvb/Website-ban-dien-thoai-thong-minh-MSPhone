package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "IMAGE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class image {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "SanPham_ID")
    private SanPham sanPhamId;

    @Column(name = "TenAnh")
    private String tenAnh;

    @Column(name = "MauSacId")
    private Integer mauSacId;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
