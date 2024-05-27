package com.example.demo.Repository;

import com.example.demo.Entitys.SanPham;
import com.example.demo.Entitys.image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<image,Integer> {
    SanPham findAllBySanPhamId(SanPham sp);
}
