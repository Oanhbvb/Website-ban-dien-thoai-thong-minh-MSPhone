package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "IDKhachHang")
    private Integer idKhachHang;
    @Column(name = "IDNhanVien")
    private Integer idNhanVien;
    @Column(name = "IDPhieuGiamGia")
    private Integer idPhieuGiamGia;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NgayTao")
    private Date ngayTao;
    @Column(name = "TongTien")
    private Double tongTien;
    @Column(name = "TongTienSauGiam")
    private Double tongTienSauGiam;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NgayThanhToan")
    private Date ngayThanhToan;
    @Column(name = "LoaiHoaDon")
    private Integer loaiHoaDon;
    @Column(name = "TenNguoiNhan")
    private  String tenNguoiNhan;
    @Column(name = "SDTNguoiNhan")
    private  String sdtNguoiNhan;
    @Column(name = "GhiChu")
    private String ghiChu;
    @Column(name = "MaQR")
    private String maQR;
    @Column(name = "PhiShip")
    private String phiShip;
    @Column(name = "DiaChi")
    private String diaChi;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NgayNhanHang")
    private Date ngayNhanHang;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NgayMongMuonNhanHang")
    private Date ngayMongMuonNhanHang;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name ="NgayHuy" )
    private Date ngayHuy;
    @Column(name = "TrangThai")
    private Integer trangThai;
    @Column(name = "CoXoa")
    private Integer coXoa;

}
