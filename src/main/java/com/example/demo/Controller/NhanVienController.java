package com.example.demo.Controller;

import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

import com.example.demo.Entitys.ChucVu;
import com.example.demo.Entitys.NhanVien;
import com.example.demo.Repository.ChucVuRepository;
import com.example.demo.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"nhan-vien"})
public class NhanVienController {
    @Autowired
    private NhanVienRepository nvRepo;
    @Autowired
    private ChucVuRepository cvRepo;
    String success;
    String error;

    public NhanVienController() {
    }

    @GetMapping({"hien-thi"})
    public String hienThi(Model model, @RequestParam("page") Optional<Integer> pageParam) {
        int page = (Integer)pageParam.orElse(0);
        Pageable p = PageRequest.of(page, 5, Sort.by(Direction.DESC, new String[]{"id"}));
        Page<NhanVien> listNV = this.nvRepo.findAll(p);
        model.addAttribute("listNV", listNV);
        model.addAttribute("success", this.success);
        model.addAttribute("error", this.error);
        this.success = null;
        return "nhan-vien/index";
    }

    @PostMapping({"search"})
    public String search(@RequestParam("search") String search, @RequestParam("page") Optional<Integer> pageParam, Model model) {
        int page = (Integer)pageParam.orElse(0);
        Pageable p = PageRequest.of(page, 5);
        Page<NhanVien> listSearch = this.nvRepo.findAllByMaNhanVienOrTenNhanVienContainingOrSdtContaining(search, search, search, p);
        model.addAttribute("listNV", listSearch);
        return "nhan-vien/index";
    }

    @GetMapping({"view-add"})
    public String viewAdd(Model model) {
        NhanVien nv = new NhanVien();
        nv.setIdChucVu((ChucVu) this.cvRepo.findById(2).get());
        model.addAttribute("nhanVien", nv);
        return "nhan-vien/add";
    }

    @PostMapping({"add"})
    public String add(@ModelAttribute("nhanVien") NhanVien nv, Model model, @RequestParam("avatar") MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "/assets/img/avatars";
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            InputStream inputStream = file.getInputStream();

            try {
                Files.copy(inputStream, filePath, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                nv.setAnhNhanVien(filePath.toString());
            } catch (Throwable var13) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var12) {
                        var13.addSuppressed(var12);
                    }
                }

                throw var13;
            }

            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }

        try {
            int var10001 = this.nvRepo.findAll().size();
            nv.setMaNhanVien("NV" + (var10001 + 1));
            nv.setTrangThai(1);
            nv.setNgayTao(new Date());
            this.nvRepo.save(nv);
            this.success = "Thêm nhân viên thành công";
        } catch (Exception var11) {
            this.success = null;
            var11.printStackTrace();
        }

        return "redirect:/nhan-vien/hien-thi";
    }

    @GetMapping({"view-update/{id}"})
    public String viewUpdate(Model model, @PathVariable("id") NhanVien nv) {
        model.addAttribute("nhanVien", nv);
        model.addAttribute("maNV", "MÃ NHÂN VIÊN: " + nv.getMaNhanVien());
        return "nhan-vien/update";
    }

    @PostMapping({"update/{id}"})
    public String update(@ModelAttribute("nhanVien") NhanVien nv, Model model, @RequestParam("avatar") MultipartFile file) {
        NhanVien nhanVien = (NhanVien)this.nvRepo.findById(nv.getId()).get();
        nv.setMaNhanVien(nhanVien.getMaNhanVien());
        nv.setNgaySua(new Date());
        nv.setTrangThai(1);

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "/assets/img/avatars";
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            InputStream inputStream = file.getInputStream();

            try {
                Files.copy(inputStream, filePath, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                nv.setAnhNhanVien(filePath.toString());
            } catch (Throwable var14) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var13) {
                        var14.addSuppressed(var13);
                    }
                }

                throw var14;
            }

            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

        try {
            this.nvRepo.save(nv);
            this.success = "Cập nhật nhân viên thành công";
        } catch (Exception var12) {
            this.success = null;
        }

        return "redirect:/nhan-vien/hien-thi";
    }

    @GetMapping({"delete/{id}"})
    public String delete(@PathVariable("id") NhanVien nv) {
        nv.setTrangThai(0);
        this.nvRepo.save(nv);
        return "redirect:/nhan-vien/hien-thi";
    }
}





