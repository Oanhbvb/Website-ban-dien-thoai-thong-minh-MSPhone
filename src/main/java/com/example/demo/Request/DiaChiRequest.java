package com.example.demo.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaChiRequest {
    Integer id;
    @NotBlank(message = "Địa chỉ cụ thể không được để trống")
    private String diaChiCuThe;
    @NotBlank(message = "Phường không được để trống")
    private String phuong;
    @NotBlank(message = "Quận không được để trống")
    private String quan;
    @NotBlank(message = "Tỉnh không được để trống")
    private String tinh;
}
