package com.example.demo.Repository;

import com.example.demo.Entitys.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    Page<NhanVien> findAllByMaNhanVienOrTenNhanVienContainingOrSdtContaining(String ma, String ten, String sdt, Pageable pageable);
}
