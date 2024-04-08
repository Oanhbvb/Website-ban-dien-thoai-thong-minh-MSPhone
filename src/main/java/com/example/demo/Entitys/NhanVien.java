package com.example.demo.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NhanVien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "MaNhanVien")
    private String maNhanVien;
    @Column(name = "TenNhanVien")
    private String tenNhanVien;
    @Column(name = "SDT")
    private String sdt;
    @Column(name = "Email")
    private String email;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "CCCD")
    private String CCCD;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NgaySinh")
    private Date ngaySinh;
    @Column(name = "AnhNhanVien")
    private String anhNhanVien;
    @Column(name = "MatKhau")
    private String matKhau;
    @Column(name = "GhiChu")
    private String ghiChu;
    @Column(name = "NgayTao")
    private Date ngayTao;
    @Column(name = "NgaySua")
    private Date ngaySua;
    @Column(name = "NguoiTao")
    private String nguoiTao;
    @Column(name = "NguoiSua")
    private String nguoiSua;
    @Column(name = "TrangThai")
    private Integer trangThai;
    @ManyToOne
    @JoinColumn(name = "ChucVu_ID")
    private ChucVu idChucVu;

}

