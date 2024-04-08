package com.example.demo.Repository;

import com.example.demo.Entitys.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Integer> {
}
