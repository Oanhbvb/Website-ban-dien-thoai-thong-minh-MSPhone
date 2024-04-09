package com.example.demo.Repository;

import com.example.demo.Entitys.PhieuGG_KH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PhieuGG_KHRepo extends JpaRepository<PhieuGG_KH, BigInteger> {
}
