package com.example.demo.Repository;

import com.example.demo.Entitys.PhieuGG_KH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuGG_KHRepo extends JpaRepository<PhieuGG_KH, Long> {
}
