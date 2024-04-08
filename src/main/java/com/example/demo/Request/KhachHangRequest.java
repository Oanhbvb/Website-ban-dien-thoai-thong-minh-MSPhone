package com.example.demo.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangRequest {
    private Integer id;
    private  String maKhachHang;
    @NotBlank(message = "Tên khách hàng hông được để trống")
    private  String tenKhachHang;
    @NotBlank(message = "SDT không được trống")
    private String sdt;
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;
    private String matKhau;
    private Integer trangThai;
    @NotNull(message = "Địa chỉ không được trống")
    private Integer idDiaChi;
    private  String anhKhachHang;


}
