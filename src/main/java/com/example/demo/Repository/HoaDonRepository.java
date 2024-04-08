package com.example.demo.Repository;

import com.example.demo.Entitys.HoaDon;
import com.example.demo.Entitys.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepository  extends JpaRepository<HoaDon, Integer> {
    List<HoaDon> findAllByidKhachHang(Integer id);
}
