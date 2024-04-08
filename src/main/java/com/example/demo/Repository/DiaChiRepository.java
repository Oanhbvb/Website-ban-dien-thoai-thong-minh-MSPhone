package com.example.demo.Repository;

import com.example.demo.Entitys.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi,Integer> {
    @Query(value = "select dc from DiaChi dc  where dc.idKhachHang=:id and  dc.trangThai=:status   order by dc.id desc ")
    List<DiaChi> findAllBy(@Param("id") Integer id,@Param("status") Integer status);

    @Query(value = "select dc from DiaChi dc  where dc.idKhachHang=:id and ( dc.trangThai=:status or dc.trangThai=:status2  ) order by dc.id desc ")
    List<DiaChi> findAllBy(@Param("id") Integer id,@Param("status") Integer status,@Param("status2") Integer status2);

    @Query(value = "select dc from DiaChi dc  where dc.trangThai=:status  order by dc.id desc ")
    List<DiaChi> findAllBy(@Param("status") Integer status);

}
