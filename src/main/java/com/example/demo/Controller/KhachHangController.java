package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {

    @GetMapping("/hien-thi")
    public String hienThi(){
        return "KhachHang/index";
    }
}
