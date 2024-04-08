package com.example.demo.Repository;

import com.example.demo.Entitys.HoaDon;
import com.example.demo.Entitys.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, BigInteger> {
    Page<HoaDon> findAllByNgayTaoBetween(Date fromDate,Date toDate, Pageable pageable);
    Page<HoaDon> findAllByLoaiHoaDon(int loaiHD, Pageable pageable);
    Page<HoaDon> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd JOIN hd.idKhachHang kh WHERE hd.maHD LIKE %:keyword% OR kh.sdt LIKE %:keyword%")
    Page<HoaDon> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}

