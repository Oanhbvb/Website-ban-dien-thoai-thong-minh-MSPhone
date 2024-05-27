package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Table(name = "Imei")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Imei {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IDChiTietSP")
    private ChiTietSanPham idChiTietSP;

    @Column(name = "Ma_Imei")
    private String maImei;

}
