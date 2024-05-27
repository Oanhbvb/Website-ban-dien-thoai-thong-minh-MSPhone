package com.example.demo.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.Entitys.*;
import com.example.demo.Repository.*;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("san-pham")
public class SanPhamController {

    @Autowired
    Cloudinary cloudinary;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 10;

    @Autowired
    private HangRepository hangRepository;
    @Autowired
    private CameraSauRepository cameraSauRepository;
    @Autowired
    private CameraTruocRepository cameraTruocRepository;
    @Autowired
    private ChipRepository chipRepository;
    @Autowired
    private HeDieuHanhRepository heDieuHanhRepository;
    @Autowired
    private ManHinhRepository manHinhRepository;
    @Autowired
    private PinRepository pinRepository;
    @Autowired
    private SimRepository simRepository;
    @Autowired
    private ROMRepository romRepository;
    @Autowired
    private RAMRepository ramRepository;
    @Autowired
    private MauSacRepository mauSacRepository;
    @Autowired
    private ChiTietSanPhamRepository ctspRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private ImeiRepository imeiRepository;
    @Autowired
    ImageRepository imageRepository;

    SanPham sanPham = new SanPham();
    List<ROM> dsROM = new ArrayList<>();
    List<RAM> dsRAM = new ArrayList<>();
    List<MauSac> dsMauSac = new ArrayList<>();
    List<SanPham> lstSP = new ArrayList<>();
    List<ChiTietSanPham> lstCTSP = new ArrayList<>();
    List<List<String>> lstIM = new ArrayList<>(new ArrayList<>());
    String tenSanPham = "";

    @GetMapping("/route")
    public String yourHandlerMethod() {
        return "redirect:/san-pham/index";
    }

    @GetMapping("/index")
    public String index(@RequestParam("page") Optional<Integer> pageParam, Model model) {

        int page = pageParam.orElse(0);
        Pageable pageable = PageRequest.of(page, 10);
        Page<SanPham> pageData = sanPhamRepository.findBy(pageable);
        model.addAttribute("data", pageData);
        model.addAttribute("lstPin", pinRepository.findAll());
        model.addAttribute("lstHang", hangRepository.findAll());
        model.addAttribute("lstHeDieuHanh", heDieuHanhRepository.findAll());
        model.addAttribute("lstManHinh", manHinhRepository.findAll());
        model.addAttribute("message_title1", "Quản lý sản phẩm");
        model.addAttribute("message_title2", "Danh sách sản phẩm");

        return "SanPham/index";
    }

    @GetMapping("create-product")
    public String create(Model model) {
        model.addAttribute("lstHang", hangRepository.findByTrangThai(HangRepository.ACTIVE));
        model.addAttribute("lstCameraTruoc", cameraTruocRepository.findByTrangThai(CameraTruocRepository.ACTIVE));
        model.addAttribute("lstCameraSau", cameraSauRepository.findByTrangThai(CameraSauRepository.ACTIVE));
        model.addAttribute("lstChip", chipRepository.findByTrangThai(ChipRepository.ACTIVE));
        model.addAttribute("lstHeDieuHanh", heDieuHanhRepository.findByTrangThai(HeDieuHanhRepository.ACTIVE));
        model.addAttribute("lstManHinh", manHinhRepository.findByTrangThai(ManHinhRepository.ACTIVE));
        model.addAttribute("lstPin", pinRepository.findByTrangThai(PinRepository.ACTIVE));
        model.addAttribute("lstSim", simRepository.findByTrangThai(SimRepository.ACTIVE));
        model.addAttribute("lstRom", romRepository.findByTrangThai(ROMRepository.ACTIVE));
        model.addAttribute("lstRam", ramRepository.findByTrangThai(RAMRepository.ACTIVE));
        model.addAttribute("lstMauSac", mauSacRepository.findByTrangThai(MauSacRepository.ACTIVE));
        model.addAttribute("lstCTSP", lstCTSP);
        model.addAttribute("dsMauSac", dsMauSac);
        model.addAttribute("dsROM", dsROM);
        model.addAttribute("dsRAM", dsRAM);
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("tenSanPham", tenSanPham);
        model.addAttribute("lstIM", lstIM);
        return "SanPham/create";
    }

    @GetMapping("view-update/{id}")
    public String ViewUpdate(@PathVariable("id") SanPham sanPham, Model model) {
        model.addAttribute("lstHang", hangRepository.findByTrangThai(HangRepository.ACTIVE));
        model.addAttribute("lstCameraTruoc", cameraTruocRepository.findByTrangThai(CameraTruocRepository.ACTIVE));
        model.addAttribute("lstCameraSau", cameraSauRepository.findByTrangThai(CameraSauRepository.ACTIVE));
        model.addAttribute("lstChip", chipRepository.findByTrangThai(ChipRepository.ACTIVE));
        model.addAttribute("lstHeDieuHanh", heDieuHanhRepository.findByTrangThai(HeDieuHanhRepository.ACTIVE));
        model.addAttribute("lstManHinh", manHinhRepository.findByTrangThai(ManHinhRepository.ACTIVE));
        model.addAttribute("lstPin", pinRepository.findByTrangThai(PinRepository.ACTIVE));
        model.addAttribute("lstSim", simRepository.findByTrangThai(SimRepository.ACTIVE));
        model.addAttribute("lstRom", romRepository.findByTrangThai(ROMRepository.ACTIVE));
        model.addAttribute("lstRam", ramRepository.findByTrangThai(RAMRepository.ACTIVE));
        model.addAttribute("lstMauSac", mauSacRepository.findByTrangThai(MauSacRepository.ACTIVE));
        model.addAttribute("lstCTSP", lstCTSP);
        model.addAttribute("dsMauSac", dsMauSac);
        model.addAttribute("dsROM", dsROM);
        model.addAttribute("dsRAM", dsRAM);
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("tenSanPham", tenSanPham);
        model.addAttribute("lstIM", lstIM);
        model.addAttribute("message_title1", "Quản lý sản phẩm");
        model.addAttribute("message_title2", "Cập nhật sản phẩm");
        return "SanPham/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, SanPham sanPham,
                         Model model) {

        Optional<SanPham> optionalSanPham = sanPhamRepository.findById(id);
        SanPham sanPham1 = optionalSanPham.get();
        sanPham1.setTenSanPham(sanPham.getTenSanPham());
        sanPham1.setIdCameraSau(sanPham.getIdCameraSau());
        sanPham1.setIdChip(sanPham.getIdChip());
        sanPham1.setIdCameraTruoc(sanPham.getIdCameraTruoc());
        sanPham1.setIdHeDieuHanh(sanPham.getIdHeDieuHanh());
        sanPham1.setIdManHinh(sanPham.getIdManHinh());
        sanPham1.setIdPin(sanPham.getIdPin());
        sanPham1.setIdSim(sanPham.getIdSim());
        sanPham1.setNgaySua(new Date());
        sanPhamRepository.save(sanPham1);
        return "redirect:/san-pham/index";

    }

    @PostMapping("store")
    public String store(@RequestParam("tenSanPham") String tenSanPham,
                        @RequestParam("idHeDieuHanh") int idHeDieuHanh,
                        @RequestParam("idManHinh") int idManHinh,
                        @RequestParam("idHang") int idHang,
                        @RequestParam("idCameraTruoc") int idCameraTruoc,
                        @RequestParam("idCameraSau") List idCameraSau,
                        @RequestParam("idCameraChinhSau") Integer idCameraChinhSau,
                        @RequestParam("idSim") int idSim,
                        @RequestParam("idPin") int idPin,
                        @RequestParam("idChip") int idChip,
                        @RequestParam(value = "idRAM", defaultValue = "") List<Integer> lstIdRam,
                        @RequestParam(value = "idROM", defaultValue = "") List<Integer> lstIdRom,
                        @RequestParam(value = "idMauSac", defaultValue = "") List<Integer> lstIdMauSac,
                        Model model
    ) throws IOException {

        System.out.println(idCameraSau);
        System.out.println(idCameraChinhSau);

        if (tenSanPham.isEmpty()) {
            model.addAttribute("message", "nhập tên");
            create(model);
            return "SanPham/create";
        }
        if (lstIdRam.isEmpty()) {
            model.addAttribute("messageRam", "nhập Ram");
            create(model);
            return "SanPham/create";
        }
        if (lstIdRom.isEmpty()) {
            model.addAttribute("messageRom", "nhập Rom");
            create(model);
            return "SanPham/create";
        }

        SanPham sanPhamRq = new SanPham();
        sanPhamRq.setTenSanPham(tenSanPham);
        HeDieuHanh hdh = new HeDieuHanh();
        hdh.setId(idHeDieuHanh);
        sanPhamRq.setIdHeDieuHanh(hdh);
        ManHinh mh = new ManHinh();
        mh.setId(idManHinh);
        sanPhamRq.setIdManHinh(mh);
        Hang hang = new Hang();
        hang.setId(idHang);
        sanPhamRq.setIdHang(hang);
        CameraTruoc cameraTruoc = new CameraTruoc();
        cameraTruoc.setId(idCameraTruoc);
        sanPhamRq.setIdCameraTruoc(cameraTruoc);
//        CameraSau cameraSau = new CameraSau();
//        cameraSau.setId(idCameraSau);
//        sanPhamRq.setIdCameraSau(cameraSau);
        Sim sim = new Sim();
        sim.setId(idSim);
        sanPhamRq.setIdSim(sim);
        Pin pin = new Pin();
        pin.setId(idPin);
        sanPhamRq.setIdPin(pin);
        Chip chip = new Chip();
        chip.setId(idChip);
        sanPhamRq.setIdChip(chip);
        for (Integer id : lstIdRam) {
            RAM ram = ramRepository.findById(id).get();
            dsRAM.add(ram);
        }

        for (Integer id : lstIdRom) {
            ROM rom = romRepository.findById(id).get();
            dsROM.add(rom);
        }

        for (Integer id : lstIdMauSac) {
            MauSac mauSac = mauSacRepository.findById(id).get();
            dsMauSac.add(mauSac);
        }


        int count = 0;
        for (Integer idRam : lstIdRam) {
            for (Integer idRom : lstIdRom) {
                for (Integer idMauSac : lstIdMauSac) {
                    ChiTietSanPham ctsp = new ChiTietSanPham();
                    SanPham sp = new SanPham();
                    sp.setTenSanPham(sanPhamRq.getTenSanPham());
                    RAM ram = new RAM();
                    ram.setId(idRam);
                    ROM rom = new ROM();
                    rom.setId(idRom);
                    MauSac mauSac = new MauSac();
                    mauSac.setId(idMauSac);

                    count = count + 1;
                    int maxId = 0;
                    Optional<ChiTietSanPham> maxIdctspn = ctspRepository.findMaId();
                    if (maxIdctspn.isPresent()) {
                        maxId = maxIdctspn.get().getId() + count;
                    }
                    if (maxIdctspn.isEmpty()) {
                        maxId = count;
                    }
                    ctsp.setMaChiTietSanPham("SPCT" + maxId);
                    ctsp.setIdSanPham(sp);
                    ctsp.setIdROM(rom);
                    ctsp.setIdRAM(ram);
                    ctsp.setIdMauSac(mauSac);
                    ctsp.setTrangThai(ChiTietSanPhamRepository.ACTIVE);
                    lstCTSP.add(ctsp);
                    List<String> lstIMForCTSP = new ArrayList<>();
                    lstIM.add(lstIMForCTSP);

                }
            }
        }

        count = 0;
        Optional<SanPham> maxIdSP = sanPhamRepository.findMaId();
        int maxId = maxIdSP.isPresent() ? maxIdSP.get().getId() + 1 : 1;
        sanPhamRq.setMaSanPham("SP" + maxId);
        sanPhamRq.setTrangThai(1);
        sanPham = sanPhamRepository.save(sanPhamRq);
        return "redirect:/san-pham/create-product";
    }

    @GetMapping("/delete/{idRAM}/{idROM}/{idMauSac}")
    public String delete(
            @PathVariable("idRAM") RAM idRAM,
            @PathVariable("idROM") ROM idROM,
            @PathVariable("idMauSac") MauSac idMauSac) {

        for (int i = 0; i < lstCTSP.size(); i++) {
            if (lstCTSP.get(i).getIdRAM().getId() == idRAM.getId().intValue() &&
                    lstCTSP.get(i).getIdROM().getId() == idROM.getId().intValue() &&
                    lstCTSP.get(i).getIdMauSac().getId() == idMauSac.getId().intValue()) {
                lstCTSP.remove(i);

                for (int j = 0; j < lstIM.size(); j++) {
                    lstIM.remove(i);
                    break;
                }
            }
        }

        return "redirect:/san-pham/create-product";

    }

    @GetMapping("/delete/{idRAM}/{idROM}")
    public String deletePB(
            @PathVariable("idRAM") RAM idRAM,
            @PathVariable("idROM") ROM idROM) {

//        lstCTSP.removeIf(x -> x.getIdRAM().getId().intValue() == idRAM.getId().intValue() &&
//                x.getIdROM().getId().intValue() == idROM.getId().intValue());
        List<ChiTietSanPham> removeCTSP = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < lstCTSP.size(); i++) {
            if (lstCTSP.get(i).getIdRAM().getId() == idRAM.getId().intValue() &&
                    lstCTSP.get(i).getIdROM().getId() == idROM.getId().intValue()) {
//                lstCTSP.remove(i);
                removeCTSP.add(lstCTSP.get(i));
                list.add(i);
            }
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            int indexToRemove = list.get(i);
            lstIM.remove(indexToRemove);
        }
        lstCTSP.removeAll(removeCTSP);
        System.out.println(lstCTSP.size());
        System.out.println(lstIM.size());

        return "redirect:/san-pham/create-product";
    }


    @PostMapping("/updatePrice")
    public String updatePrice(@RequestParam("price") List<BigDecimal> lstPrice,
                              @RequestParam("idRam") List<Integer> idRams,
                              @RequestParam("idRom") List<Integer> idRoms,
                              @RequestParam("idMauSac") List<Integer> idMauSacs,
                              @RequestParam("image") List<MultipartFile> images,
                              @RequestParam(value = "images1",required = false)  List<MultipartFile> listImages1,
                              @RequestParam(value = "images2",required = false) List<MultipartFile> listImages2
//                               @RequestParam Map<String, List<MultipartFile>>imagesMap
                              ) throws IOException {

//        for (int i = 0; i < dsMauSac.size(); i++) {
//            Integer colorId = dsMauSac.get(i).getId();
//            String paramName = "images" + colorId;
//            List<MultipartFile> listImages = imagesMap.get(paramName);
//            System.out.println(listImages.size());
//        }

        System.out.println(images);
        System.out.println(listImages1.size());
        System.out.println(listImages2.size());

//        for (int i = 0; i < lstCTSP.size(); i++) {
//            for (MultipartFile multipartFile : multipartFiles) {
//                try (InputStream excelFile = multipartFile.getInputStream()) {
//                    Workbook workbook = new XSSFWorkbook(excelFile);
//                    Sheet datatypeSheet = workbook.getSheetAt(0);
//                    DataFormatter fmt = new DataFormatter();
//                    Iterator<Row> iterator = datatypeSheet.iterator();
//                    Row firstRow = iterator.next();
//                    Cell firstCell = firstRow.getCell(0);
//                    System.out.println(firstCell.getStringCellValue());
//                    List<Imei> listOfNhanVien = new ArrayList<Imei>();
//
//                    while (iterator.hasNext()) {
//                        Row currentRow = iterator.next();
//                        Imei nv = new Imei();
//                        nv.setMaImei(currentRow.getCell(0).getStringCellValue());
//                        listOfNhanVien.add(nv);
//                    }
//                    workbook.close();
//
//
//                    for (int j = 0; j < listOfNhanVien.size(); j++) {
//                        lstIM.get(i).add("IM" + listOfNhanVien.get(j).getMaImei());
//
//                    }
//                    multipartFiles.remove(multipartFile);
//
//                } catch (IOException | RuntimeException e) {
//                    e.printStackTrace();
//                    System.out.println("Có lỗi xảy ra: " + e.getMessage());
//                    return "redirect:/san-pham/create-product";
//                }
//                break;
//            }
//
//        }

        ///////////////////////

        for (int i = 0; i < lstCTSP.size(); i++) {
            lstCTSP.get(i).setGiaBan(lstPrice.get(i));
            lstCTSP.get(i).setIdSanPham(sanPham);
        }
        List<ChiTietSanPham> ctspSave = ctspRepository.saveAll(lstCTSP);

        /////////////////

        List<image> listImage = new ArrayList<>();
        if (images != null || images.isEmpty()) {
            for (MultipartFile file : images) {
                Map r = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                image image = new image();
                image.setTenAnh((String) r.get("secure_url"));
                listImage.add(image);
            }
        }

        List<image> imageTotals = new ArrayList<>();
            for (image i : listImage) {
                image newImage = new image();
                newImage.setSanPhamId(sanPham);
                newImage.setTenAnh(i.getTenAnh());
                newImage.setTrangThai(1);
                imageTotals.add(newImage);
            }
        imageRepository.saveAll(imageTotals);

        List<image> listImage1= new ArrayList<>();
        if (images != null || images.isEmpty()) {
            for (MultipartFile file : listImages1) {
                Map r = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                image image = new image();
                image.setTenAnh((String) r.get("secure_url"));
                listImage1.add(image);
            }
        }

        List<image> imageTotals1 = new ArrayList<>();
            for (image i : listImage1) {
                image newImage = new image();
                newImage.setSanPhamId(sanPham);
                newImage.setTenAnh(i.getTenAnh());
                newImage.setTrangThai(1);
                newImage.setMauSacId(1);
                imageTotals1.add(newImage);
            }
        imageRepository.saveAll(imageTotals1);

        //////////////

        List<Imei> lstImei = new ArrayList<>();
        for (int i = 0; i < lstCTSP.size(); i++) {
            ChiTietSanPham ctsp = lstCTSP.get(i);
            SanPham sanPham1 = new SanPham();
            sanPham1.setId(sanPham.getId());
            RAM ram = new RAM();
            ram.setId(lstCTSP.get(i).getIdRAM().getId());
            ROM rom = new ROM();
            rom.setId(lstCTSP.get(i).getIdROM().getId());
            MauSac mauSac = new MauSac();
            mauSac.setId(lstCTSP.get(i).getIdMauSac().getId());
            ChiTietSanPham findCTSP = ctspRepository.findByIdRAMAndIdROMAndIdMauSacAndIdSanPham(ram, rom, mauSac, sanPham1);
            for (int j = 0; j < lstIM.get(i).size(); j++) {
                Imei imei = new Imei();
                imei.setIdChiTietSP(findCTSP);
                imei.setMaImei(lstIM.get(i).get(j));
                lstImei.add(imei);
            }
        }
        imeiRepository.saveAll(lstImei);

        dsMauSac.clear();
        dsRAM.clear();
        dsROM.clear();
        lstIM.clear();
        sanPham = new SanPham();
        lstCTSP.clear();

        return "redirect:/san-pham/index";
    }


    @PostMapping("/addIM/{idRAM}/{idROM}/{idMauSac}")
    public String importIM(@PathVariable("idRAM") int idRAM,
                           @PathVariable("idROM") int idROM,
                           @PathVariable("idMauSac") int idMauSac,
                           @RequestParam("inputField") String inputField) {

        List<String> inputList = Arrays.stream(inputField.split(","))
                .map(String::trim)  // Trim leading and trailing whitespace
                .map(s -> s.replaceAll("\\s+", ""))  // Remove all spaces within the string
                .filter(s -> !s.isEmpty())  // Filter out any empty strings
                .map(s -> "IM" + s)  // Add the prefix "IM" to each string
                .collect(Collectors.toList());

        inputList.forEach(System.out::println);


        for (int i = 0; i < lstCTSP.size(); i++) {
            ChiTietSanPham ctsp = lstCTSP.get(i);
            if (ctsp.getIdRAM().getId() == idRAM &&
                    ctsp.getIdROM().getId() == idROM &&
                    ctsp.getIdMauSac().getId() == idMauSac) {

                List<Imei> imeis = new ArrayList<>();
                for (String s : inputList) {
                    Imei imei = new Imei();
                    imei.setMaImei(s);
                    imeis.add(imei);
                }
                for (int j = 0; j < imeis.size(); j++) {
                    lstIM.get(i).add("IM" + imeis.get(j).getMaImei());
                }
            }
        }
        return "redirect:/san-pham/create-product";
    }

    @PostMapping("/importIM/{idRAM}/{idROM}/{idMauSac}")
    public String importIM(@PathVariable("idRAM") int idRAM,
                           @PathVariable("idROM") int idROM,
                           @PathVariable("idMauSac") int idMauSac,
                           @RequestParam("file") MultipartFile multipartFile) {

        for (int i = 0; i < lstCTSP.size(); i++) {
            ChiTietSanPham ctsp = lstCTSP.get(i);
            if (ctsp.getIdRAM().getId() == idRAM &&
                    ctsp.getIdROM().getId() == idROM &&
                    ctsp.getIdMauSac().getId() == idMauSac) {
                try (InputStream excelFile = multipartFile.getInputStream()) {
                    Workbook workbook = new XSSFWorkbook(excelFile);
                    Sheet datatypeSheet = workbook.getSheetAt(0);
                    DataFormatter fmt = new DataFormatter();
                    Iterator<Row> iterator = datatypeSheet.iterator();
                    Row firstRow = iterator.next();
                    Cell firstCell = firstRow.getCell(0);
                    List<Imei> listOfNhanVien = new ArrayList<Imei>();

                    while (iterator.hasNext()) {
                        Row currentRow = iterator.next();
                        Imei nv = new Imei();
                        nv.setMaImei(currentRow.getCell(0).getStringCellValue());
                        listOfNhanVien.add(nv);
                    }
                    workbook.close();


                    for (int j = 0; j < listOfNhanVien.size(); j++) {
                        lstIM.get(i).add("IM" + listOfNhanVien.get(j).getMaImei());

                    }

                } catch (IOException | RuntimeException e) {
                    e.printStackTrace();
                    System.out.println("Có lỗi xảy ra: " + e.getMessage());
                }

            }

        }


        return "redirect:/san-pham/create-product";
    }

    @GetMapping("/detail/{id}")
    public String getCTSP(@PathVariable("id") Integer idSP,
                          @RequestParam("page") Optional<Integer> pageParam,
                          Model model, HttpSession session) {
        List<List<Imei>> lstImeiDetail = new ArrayList<>(new ArrayList<>());

        int page = pageParam.orElse(0);
        Pageable pageable = PageRequest.of(page, 10);
        Page<ChiTietSanPham> lstCTSP = ctspRepository.findByIdSanPham(pageable, idSP);
        lstCTSP.forEach(x -> System.out.println(x.getId()));
        model.addAttribute("lstRom", romRepository.findByTrangThai(ROMRepository.ACTIVE));
        model.addAttribute("lstRam", ramRepository.findByTrangThai(RAMRepository.ACTIVE));
        model.addAttribute("lstMauSac", mauSacRepository.findByTrangThai(MauSacRepository.ACTIVE));
        model.addAttribute("lstCTSP", lstCTSP);

        List<Integer> counts = new ArrayList<>();
        for (ChiTietSanPham chiTietSanPham : lstCTSP) {
            counts.add(imeiRepository.countIM(chiTietSanPham.getId()));
        }
        model.addAttribute("lstSL", counts);
        model.addAttribute("idSP", idSP);

        session.removeAttribute("idSP");
        session.setAttribute("idSP", idSP);

        model.addAttribute("message_title1", "Quản lý sản phẩm");
        model.addAttribute("message_title2", "Chi tiết sản phẩm");

        return "SanPham/detail";

    }

    @GetMapping("/chi-tiet-san-pham/detail/{id}")
    public String getAllIMByIdSPCT(@PathVariable("id") Integer id,
                                   @RequestParam("page") Optional<Integer> pageParam,
                                   Model model) {
        int page = pageParam.orElse(0);
        Pageable p = PageRequest.of(page, 5);
        Page<Imei> list = imeiRepository.findAllByIdChiTietSanPham(id, p);
        model.addAttribute("idSPCT", id);

        model.addAttribute("message_title1", "Quản lý sản phẩm");
        model.addAttribute("message_title2", "Danh sách imei sản phẩm");
        model.addAttribute("listIM", list);
        return "SanPham/spct_detail";
    }

//    @PostMapping("/import-spct/{id}")
//    public String importSPC (@PathVariable("id") ChiTietSanPham chiTietSanPham,
//                             @RequestParam("file") MultipartFile multipartFile,
//                             @RequestParam("idURL")Integer id,
//                             Model model){
//
//        try (InputStream excelFile = multipartFile.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(excelFile);
//            Sheet datatypeSheet = workbook.getSheetAt(0);
//            DataFormatter fmt = new DataFormatter();
//            Iterator<Row> iterator = datatypeSheet.iterator();
//            Row firstRow = iterator.next();
//            Cell firstCell = firstRow.getCell(0);
//            System.out.println(firstCell.getStringCellValue());
//            List<Imei> listOfNhanVien = new ArrayList<Imei>();
//
//            while (iterator.hasNext()) {
//                Row currentRow = iterator.next();
//                Imei nv = new Imei();
//                nv.setMaImei(currentRow.getCell(0).getStringCellValue());
//                listOfNhanVien.add(nv);
//            }
//            workbook.close();
//
//            listOfNhanVien.forEach(x->x.setIdChiTietSP(chiTietSanPham));
//            listOfNhanVien.forEach(x-> System.out.println(x.getMaImei()));
//            imeiRepository.saveAll(listOfNhanVien);
//
//
//        } catch (IOException | RuntimeException e) {
//            e.printStackTrace();
//            System.out.println("Có lỗi xảy ra: " + e.getMessage());
//            return null;
//        }
//
//        return "redirect:/san-pham/detail/"+id;
//    }


    @PostMapping("/import-spct/{id}")
    public String importSPC(@PathVariable("id") Integer id,
                            @RequestParam("file") MultipartFile multipartFile,
                            Model model) {

        Optional<ChiTietSanPham> optionalChiTietSanPham = ctspRepository.findById(id);
        try (InputStream excelFile = multipartFile.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            Cell firstCell = firstRow.getCell(0);
            System.out.println(firstCell.getStringCellValue());
            List<Imei> listOfNhanVien = new ArrayList<Imei>();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Imei nv = new Imei();
                nv.setMaImei(currentRow.getCell(0).getStringCellValue());
                listOfNhanVien.add(nv);
            }
            workbook.close();

            listOfNhanVien.forEach(x -> x.setMaImei("IM" + x.getMaImei()));

            List<Imei> customersToRemove = new ArrayList<>();
            List<Imei> list = imeiRepository.findAllByIdChiTietSanPham(id);
            for (int i = 0; i < listOfNhanVien.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (listOfNhanVien.get(i).getMaImei().equals(list.get(j).getMaImei())) {
                        customersToRemove.add(listOfNhanVien.get(i));

                    }
                }
            }
            listOfNhanVien.removeAll(customersToRemove);

            listOfNhanVien.forEach(x -> x.setIdChiTietSP(optionalChiTietSanPham.get()));
            imeiRepository.saveAll(listOfNhanVien);


        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            System.out.println("Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/san-pham/chi-tiet-san-pham/detail/" + id;
        }

        return "redirect:/san-pham/chi-tiet-san-pham/detail/" + id;
    }

    @PostMapping("/chi-tiet-san-pham/updateIM/{id}")
    public String updateIM(@PathVariable("id") Integer id,
                           @RequestParam("inputField") String inputField) {
        List<String> inputList = Arrays.stream(inputField.split(","))
                .map(String::trim)  // Trim leading and trailing whitespace
                .map(s -> s.replaceAll("\\s+", ""))  // Remove all spaces within the string
                .filter(s -> !s.isEmpty())  // Filter out any empty strings
                .map(s -> "IM" + s)  // Add the prefix "IM" to each string
                .collect(Collectors.toList());

        inputList.forEach(System.out::println);

        Optional<ChiTietSanPham> optionalChiTietSanPham = ctspRepository.findById(id);

        List<String> customersToRemove = new ArrayList<>();
        List<Imei> list = imeiRepository.findAllByIdChiTietSanPham(id);
        for (int i = 0; i < inputList.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (inputList.get(i).equals(list.get(j).getMaImei())) {
                    customersToRemove.add(inputList.get(i));
                }
            }
        }
        List<Imei> imeis = new ArrayList<>();
        inputList.removeAll(customersToRemove);
        for (String s : inputList) {
            Imei imei = new Imei();
            imei.setIdChiTietSP(optionalChiTietSanPham.get());
            imei.setMaImei(s);
            imeis.add(imei);
        }

        imeiRepository.saveAll(imeis);


        return "redirect:/san-pham/chi-tiet-san-pham/detail/" + id;
    }

    @GetMapping("/chi-tiet-san-pham/delete/{id}")
    public String deleteCTSP(@PathVariable("id") Integer id, HttpSession session) {
        Optional<ChiTietSanPham> optionalChiTietSanPham = ctspRepository.findById(id);
        if (optionalChiTietSanPham.isPresent()) {
            optionalChiTietSanPham.get().setTrangThai(0);
            ctspRepository.save(optionalChiTietSanPham.get());
        }
        Integer url = (Integer) session.getAttribute("idSP");
        return "redirect:/san-pham/detail/" + url;
    }

    @PostMapping("/chi-tiet-san-pham/update-price/{id}")
    public String updatePriceCTSP(@PathVariable("id") Integer id, @RequestParam("price") BigDecimal price, HttpSession session) {
        Optional<ChiTietSanPham> optionalChiTietSanPham = ctspRepository.findById(id);
        if (optionalChiTietSanPham.isPresent()) {
            optionalChiTietSanPham.get().setGiaBan(price);
            ctspRepository.save(optionalChiTietSanPham.get());
        }
        Integer url = (Integer) session.getAttribute("idSP");
        return "redirect:/san-pham/detail/" + url;
    }

    @PostMapping("/chi-tiet-san-pham/update-bulk-price")
    public String updatePrice2CTSP(@RequestBody Map<String, Object> payload, HttpSession session
    ) {
        List<String> ids = Collections.singletonList(MapUtils.getString(payload, "ids"));
        BigDecimal price = new BigDecimal(MapUtils.getString(payload, "price"));
        List<Integer> integerIds = new ArrayList<>();
        for (String id : ids) {
            String[] idArray = id.replaceAll("[\\[\\]]", "").split(", ");
            for (String num : idArray) {
                integerIds.add(Integer.parseInt(num));
            }
        }
        List<ChiTietSanPham> chiTietSanPhams = ctspRepository.findAllById(integerIds);
        for (ChiTietSanPham chiTietSanPham : chiTietSanPhams
        ) {
            chiTietSanPham.setGiaBan(price);
        }
        ctspRepository.saveAll(chiTietSanPhams);
        Integer url = (Integer) session.getAttribute("idSP");

        return "redirect:/san-pham/detail/" + url;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Optional<SanPham> optionalSP = sanPhamRepository.findById(id);
        if (optionalSP.isPresent()) {
            optionalSP.get().setTrangThai(0);
            sanPhamRepository.save(optionalSP.get());
        }
        return "redirect:/san-pham/index";
    }

    @RequestMapping("/search/product")
    public String searchProduct(@RequestParam("page") Optional<Integer> pageRequest,
                                @RequestParam(defaultValue = "", name = "search") String search,
                                @RequestParam(defaultValue = "", name = "idHeDieuHanh") Integer idHeDieuHanh,
                                @RequestParam(defaultValue = "", name = "idHang") Integer idHang,
                                @RequestParam(defaultValue = "", name = "idManHinh") Integer idManHinh,
                                @RequestParam(defaultValue = "", name = "status") Integer status,
                                Model model
    ) {


        int page = pageRequest.orElse(0);
        Pageable pageable = PageRequest.of(page, 10);

        System.out.println(search);
        System.out.println(idHang);
        System.out.println(idHeDieuHanh);
        System.out.println(idHeDieuHanh);
        System.out.println(status);
        Page<SanPham> data = sanPhamRepository.search(idHang, idHeDieuHanh, idManHinh, search, status, pageable);

        List<Pin> pins = pinRepository.findAllBy(1);
        model.addAttribute("lstPin", pins);
        model.addAttribute("lstManHinh", manHinhRepository.findByTrangThai(ManHinhRepository.ACTIVE));
        model.addAttribute("lstHeDieuHanh", heDieuHanhRepository.findByTrangThai(HeDieuHanhRepository.ACTIVE));
        model.addAttribute("lstHang", hangRepository.findByTrangThai(HangRepository.ACTIVE));
        model.addAttribute("data", data);
        model.addAttribute("checkSearch", true);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("idHang", idHang);
        model.addAttribute("idHeDieuHanh", idHeDieuHanh);
        model.addAttribute("idManHinh", idManHinh);

        return "SanPham/index";
    }


    @GetMapping("them/huy")
    public String huy() {
        lstCTSP.clear();
        dsMauSac.clear();
        dsRAM.clear();
        dsROM.clear();
        lstIM.clear();
        sanPham = new SanPham();
        return "redirect:/san-pham/create-product";

    }

    @GetMapping("test")
    public String test() {
        return "sanPham/test";
    }

//    public int countExist(int idRAM, int idROM) {
//        int count = 0;
//        for (int i = 0; i < lstCTSP.size(); i++) {
//            if (lstCTSP.get(i).getIdRAM().getId().intValue() == idRAM &&
//                    lstCTSP.get(i).getIdROM().getId().intValue() == idROM) {
//                count++;
//            }
//        }
//
//        return count;
//    }
//
//    public String createMaCTSP(int count) {
//        int index = ctspRepository.findAll().size() + count + 1;
//        String sizeToString = String.valueOf(index);
//        char character = '0';
//
//        String repeatString = String.valueOf(character).repeat(9 - sizeToString.length());
//        String maCTSP = "SP" + repeatString + index;
//        return maCTSP;
//    }
//
//    public String generateMaSP() {
//        SecureRandom random = new SecureRandom();
//        StringBuilder code = new StringBuilder(CODE_LENGTH);
//
//        for (int i = 0; i < CODE_LENGTH; i++) {
//            int randomIndex = random.nextInt(CHARACTERS.length());
//            code.append(CHARACTERS.charAt(randomIndex));
//        }
//        return code.toString();
//    }

//    public String generateImei() {
//        // Khởi tạo đối tượng Random
//        Random random = new Random();
//
//        // Dãy số ngẫu nhiên gồm 9 chữ số
//        StringBuilder randomNumber = new StringBuilder();
//        for (int i = 0; i < 9; i++) {
//            randomNumber.append(random.nextInt(10)); // Sinh số ngẫu nhiên từ 0 đến 9 và thêm vào chuỗi
//        }
//
//        return randomNumber.toString();
//    }
}
