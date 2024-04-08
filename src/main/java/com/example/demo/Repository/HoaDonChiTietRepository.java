package com.example.demo.Repository;

import com.example.demo.Entitys.ChiTietPhuongThucTT;
import com.example.demo.Entitys.HoaDon;
import com.example.demo.Entitys.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, BigInteger> {

}
