package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HoaDonChiTiet")
public class HoaDonChiTiet {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @ManyToOne
    @JoinColumn(name = "IDChiTietSP")
    private HoaDon idChiTietSP;
    @ManyToOne
    @JoinColumn(name = "IDHoaDon")
    private HoaDon idHoaDon;
    @Column(name = "Gia")
    private BigDecimal gia;
    @Column(name = "GhiChu")
    private String ghiChu;
    @Column(name = "TrangThai")
    private Integer trangThai;
}
