package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ChiTietPhuongThucTT")
public class ChiTietPhuongThucTT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private BigInteger id;
    @ManyToOne
    @JoinColumn(name = "IDHoaDon")
    private HoaDon idHoaDon;
    @ManyToOne
    @JoinColumn(name = "IDHinhThuc")
    private HinhThucThanhToan idHinhThuc;
}
