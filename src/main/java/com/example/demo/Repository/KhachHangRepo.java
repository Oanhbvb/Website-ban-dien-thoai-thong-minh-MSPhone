package com.example.demo.Repository;

import com.example.demo.Entitys.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface KhachHangRepo extends JpaRepository<KhachHang, Long> {
}
