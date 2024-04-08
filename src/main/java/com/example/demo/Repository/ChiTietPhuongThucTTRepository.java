package com.example.demo.Repository;

import com.example.demo.Entitys.ChiTietPhuongThucTT;
import com.example.demo.Entitys.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ChiTietPhuongThucTTRepository extends JpaRepository<ChiTietPhuongThucTT, BigInteger> {
    List<ChiTietPhuongThucTT> findAllByIdHoaDon(HoaDon idHoaDon);
}