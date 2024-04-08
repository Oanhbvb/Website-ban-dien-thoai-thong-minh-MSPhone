package com.example.demo.Controller;

import com.example.demo.Entitys.*;
import com.example.demo.Repository.ChiTietPhuongThucTTRepository;
import com.example.demo.Repository.HoaDonRepository;
import com.example.demo.Repository.LichSuHoaDonRepository;
import com.example.demo.Beans.StatusTextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@Controller
@RequestMapping("quan-ly-don-hang")
public class HoaDonController {
    @Autowired
    private LichSuHoaDonRepository lichSuHoaDonRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private StatusTextHelper statusTextHelper;

    @Autowired
    private ChiTietPhuongThucTTRepository chiTietPTTTRepo;

    String success;
    String error;

    @GetMapping("hien-thi")
    public String hienThi(Model model, @RequestParam("page") Optional<Integer> pageParam) {
        int page = (Integer) pageParam.orElse(0);
        Pageable p = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, new String[]{"id"}));
        Page<HoaDon> listHD = this.hoaDonRepository.findAll(p);
        model.addAttribute("listHD", listHD);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", listHD.getTotalPages());
        model.addAttribute("number", listHD.getNumber());
        return "quan-ly-don-hang/index";
    }

    @PostMapping({"search"})
    public String search(@RequestParam("search") String search,
                         @RequestParam("page") Optional<Integer> pageParam, Model model) {
        int page = pageParam.orElse(0);
        Pageable p = PageRequest.of(page, 5);

        Page<HoaDon> listHD;
        if (search == null || search.isEmpty()) {
            listHD = this.hoaDonRepository.findAll(p);
            model.addAttribute("listHD", listHD);
        } else {
            listHD = this.hoaDonRepository.searchByKeyword(search, p);
            model.addAttribute("listHD", listHD);
        }
        model.addAttribute("listHD", listHD.getContent());
        model.addAttribute("number", listHD.getNumber());
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", listHD.getTotalPages());

        return "quan-ly-don-hang/index";
    }

    @GetMapping("/search-by-date")
    public String searchByDateRange(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam("page") Optional<Integer> pageParam, Model model) {
        if (fromDate == null || toDate == null) {
            return "redirect:/quan-ly-don-hang/hien-thi";
        }
        int page = pageParam.orElse(0);
        Pageable p = PageRequest.of(page, 5);
        Page<HoaDon> listHD = this.hoaDonRepository.findAllByNgayTaoBetween(fromDate, toDate, p);
        model.addAttribute("listHD", listHD.getContent());
        model.addAttribute("number", listHD.getNumber());
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", listHD.getTotalPages());
        return "quan-ly-don-hang/index";
    }


    @GetMapping("/filter-by-loai-hd")
    public String filterOrdersByCategory(@RequestParam("loaiHoaDon") int loaiHD,
                                         Model model,
                                         @RequestParam("page") Optional<Integer> pageParam) {
        int page = pageParam.orElse(0);
        Pageable pageable = PageRequest.of(page, 5);
        Page<HoaDon> filteredOrders;

        if (loaiHD == 0) {
            filteredOrders = hoaDonRepository.findAll(pageable);
        } else {
            filteredOrders = hoaDonRepository.findAllByLoaiHoaDon(loaiHD, pageable);
        }

        model.addAttribute("listHD", filteredOrders.getContent());
        model.addAttribute("number", filteredOrders.getNumber());
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", filteredOrders.getTotalPages());

        return "quan-ly-don-hang/index";
    }

    @GetMapping("/filter-by-status")
    public String filterOrdersByStatus(@RequestParam("trangThai") int trangThai,
                                       Model model,
                                       @RequestParam("page") Optional<Integer> pageParam) {
        int page = pageParam.orElse(0);
        Pageable pageable = PageRequest.of(page, 5);
        Page<HoaDon> filteredOrders;

        if (trangThai == 0) {
            filteredOrders = hoaDonRepository.findAll(pageable);
        } else {
            filteredOrders = hoaDonRepository.findAllByTrangThai(trangThai, pageable);
        }
        model.addAttribute("number", filteredOrders.getNumber());
        model.addAttribute("listHD", filteredOrders.getContent());
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", filteredOrders.getTotalPages());

        return "quan-ly-don-hang/index";
    }

    @GetMapping("/view-detail/{id}")
    public String detail(@PathVariable("id") HoaDon hd, Model model) {
        List<LichSuHoaDon> history = lichSuHoaDonRepository.findAllByIdHoaDonOrderByThoiGianDesc(hd);
        List<LichSuHoaDon> lshd = lichSuHoaDonRepository.findAllByIdHoaDon(hd);
        int currentStatus = history.isEmpty() ? 0 : history.get(0).getTrangThai();
        if (history.isEmpty()) {
            LichSuHoaDon defaultRecord = new LichSuHoaDon(hd, hd.getIdNhanVien(), hd.getIdKhachHang(), new Date(), "Đặt hàng thành công", 1);
            lichSuHoaDonRepository.save(defaultRecord);
            history.add(defaultRecord);
        }
        List<ChiTietPhuongThucTT> listLSTT = this.chiTietPTTTRepo.findAllByIdHoaDon(hd);
        Map<Integer, String> statusIcons = new HashMap<>();
        statusIcons.put(1, "bi bi-check-circle"); // Icon cho trạng thái đặt hàng thành công
        statusIcons.put(2, "bi bi-clock"); // Icon cho trạng thái chờ giao hàng
        statusIcons.put(3, "bi bi-truck"); // Icon cho trạng thái đang giao
        statusIcons.put(4, "bi bi-house-check"); // Icon cho trạng thái giao hàng thành công
        statusIcons.put(5, "bi bi-arrow-counterclockwise"); // Icon cho trạng thái hoàn tác
        statusIcons.put(6, "bi bi-x-circle"); // Icon cho trạng thái hủy đơn hàng
        model.addAttribute("history", history);
        model.addAttribute("currentStatus", currentStatus);
        model.addAttribute("hoaDonId", hd.getId());
        model.addAttribute("statusIcons", statusIcons);
        model.addAttribute("statusTextHelper", statusTextHelper);
        model.addAttribute("hoaDon", hd);
        model.addAttribute("listLSTT", listLSTT);
        model.addAttribute("success", success);
        success = null;

        model.addAttribute("lshd", lshd);
        return "quan-ly-don-hang/detail";
    }

    @PostMapping("/cancel-order/{idHoaDon}")
    public String cancelHoaDon(@PathVariable("idHoaDon") BigInteger id,
                               @RequestParam("description") String description) {
        LichSuHoaDon newRecord = new LichSuHoaDon();
        newRecord.setIdHoaDon(hoaDonRepository.findById(id).get());
        newRecord.setGhiChu(description);
        newRecord.setThoiGian(new Date());
      //  newRecord.setIdKhachHang(hoaDonRepository.findById(id).get().getIdKhachHang());
        newRecord.setIdNhanVien(hoaDonRepository.findById(id).get().getIdNhanVien());
        newRecord.setTrangThai(6);
        lichSuHoaDonRepository.save(newRecord);
        success = "Cập nhật đơn hàng thành công!";
        return "redirect:/quan-ly-don-hang/view-detail/" + id;
    }

    @GetMapping("/confirm-order/{idHoaDon}")
    public String confirmHoaDon(@PathVariable("idHoaDon") BigInteger id) {
        LichSuHoaDon newRecord = new LichSuHoaDon();
        newRecord.setIdHoaDon(hoaDonRepository.findById(id).get());
        newRecord.setGhiChu("Xác nhận đơn hàng");
        newRecord.setThoiGian(new Date());
        //  newRecord.setIdKhachHang(hoaDonRepository.findById(id).get().getIdKhachHang());
        newRecord.setIdNhanVien(hoaDonRepository.findById(id).get().getIdNhanVien());
        newRecord.setTrangThai(2);
        lichSuHoaDonRepository.save(newRecord);
        success = "Cập nhật đơn hàng thành công!";
        return "redirect:/quan-ly-don-hang/view-detail/" + id;
    }
    @GetMapping("/ship-order/{idHoaDon}")
    public String shipHoaDon(@PathVariable("idHoaDon") BigInteger id) {
        LichSuHoaDon newRecord = new LichSuHoaDon();
        newRecord.setIdHoaDon(hoaDonRepository.findById(id).get());
        newRecord.setGhiChu("Xác nhận giao hàng cho đơn vị vận chuyển");
        newRecord.setThoiGian(new Date());
        //  newRecord.setIdKhachHang(hoaDonRepository.findById(id).get().getIdKhachHang());
        newRecord.setIdNhanVien(hoaDonRepository.findById(id).get().getIdNhanVien());
        newRecord.setTrangThai(3);
        lichSuHoaDonRepository.save(newRecord);
        success = "Cập nhật đơn hàng thành công!";
        return "redirect:/quan-ly-don-hang/view-detail/" + id;
    }
    @GetMapping("/complete-order/{idHoaDon}")
    public String completeHoaDon(@PathVariable("idHoaDon") BigInteger id) {
        LichSuHoaDon newRecord = new LichSuHoaDon();
        newRecord.setIdHoaDon(hoaDonRepository.findById(id).get());
        newRecord.setGhiChu("Giao hàng thành công");
        newRecord.setThoiGian(new Date());
        //  newRecord.setIdKhachHang(hoaDonRepository.findById(id).get().getIdKhachHang());
        newRecord.setIdNhanVien(hoaDonRepository.findById(id).get().getIdNhanVien());
        newRecord.setTrangThai(4);
        lichSuHoaDonRepository.save(newRecord);
        success = "Cập nhật đơn hàng thành công!";
        return "redirect:/quan-ly-don-hang/view-detail/" + id;
    }
    @GetMapping("/undo-order/{idHoaDon}")
    public String undoHoaDon(@PathVariable("idHoaDon") BigInteger id) {
        LichSuHoaDon lshd = lichSuHoaDonRepository.findFirstByIdHoaDonOrderByThoiGianDesc(hoaDonRepository.findById(id).get());
        lichSuHoaDonRepository.delete(lshd);
        success = "Cập nhật đơn hàng thành công!";
        return "redirect:/quan-ly-don-hang/view-detail/" + id;
    }
}
