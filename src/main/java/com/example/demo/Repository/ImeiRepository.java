package com.example.demo.Repository;

import com.example.demo.Entitys.Imei;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImeiRepository extends JpaRepository<Imei, Integer> {


    @Query("SELECT COUNT(im.idChiTietSP) FROM Imei im WHERE im.idChiTietSP.id = :id")
    Integer countIM(@Param("id") Integer id);

    @Query("select i from Imei i where i.idChiTietSP.id=:id")
    List<Imei> findAllByIdChiTietSanPham(@Param("id") Integer id);

    @Query("select i from Imei i where i.idChiTietSP.id=:id")
    Page<Imei> findAllByIdChiTietSanPham(@Param("id") Integer id, Pageable pageable);

//    @Query(value = "SELECT COALESCE(COUNT(im), 0) FROM Imei im WHERE im.idChiTietSP IN :chiTietSPIds GROUP BY im.idChiTietSP ORDER BY im.idChiTietSP.id DESC"
//         )
//    List<Integer> countByIdChiTietSanPham(@Param("chiTietSPIds") List<Integer> chiTietSPIds);


}
