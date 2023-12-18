package org.kosta.starducks.fina.controller;

import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.dto.VendorAndProductDTO;
import org.kosta.starducks.fina.entity.ContractStatus;
import org.kosta.starducks.fina.entity.VendorBusinessSector;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/fina/vendor")
public class VendorController {

    private final ModelMapper modelMapper;
    private final VendorService vendorService;

    @Autowired
    public VendorController(ModelMapper modelMapper, VendorService vendorService) {
        this.modelMapper = modelMapper;
        this.vendorService = vendorService;
    }

    /**
     * 거래처 추가
     *
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String newVendorForm(Model model) {
        log.info("여기는 컨트롤러");
        VendorAndProductDTO vendorAndProductDTO = new VendorAndProductDTO();
        model.addAttribute("vendorAndProductDTO", vendorAndProductDTO);
        model.addAttribute("businessSectors", VendorBusinessSector.values());
        return "fina/vendorAdd";
    }

    /**
     * DTO를 엔티티로 변환
     */
    private Vendor VendorEntity(VendorAndProductDTO vendorAndProductDTO) {
        return modelMapper.map(vendorAndProductDTO, Vendor.class);
    }

    /**
     * 거래처 추가 폼 데이터 받기
     *
     * @param vendorAndProductDTO
     * @return
     */
    @PostMapping("/create")
    public String newVendor(VendorAndProductDTO vendorAndProductDTO) {
        log.info("vendorAndProductDTO.toString() ==> " + vendorAndProductDTO.toString());
        // 1. DTO를 엔티티로 변환
        Vendor vendor = VendorEntity(vendorAndProductDTO);
        // 2. 리파지터리로 엔티티를 DB에 저장
        vendorService.saveVendor(vendor);
        return "redirect:/fina/vendor/list";
    }

    /**
     * 거래처 단일 조회
     *
     * @return
     */
    @GetMapping("/single/{vendorId}")
    public String showSingleVendor(@PathVariable("vendorId") int vendorId, Model model) {
        log.info("vendorId를 잘 받았는지 확인 ==> " + vendorId);
//        1. id를 조회해 데이터 가져오기
        Vendor vendorEntity = vendorService.findById(vendorId);
//        2. 모델에 데이터 등록하기
        model.addAttribute("vendor", vendorEntity);
//        3. 뷰 페이지 반환하기
        return "fina/vendorDetail";
    }

    /**
     * 거래처 전체 조회
     */
    @GetMapping("/list")
    public String showVendorList(Model model) {
//        1. 모든 데이터 가져오기
        List<Vendor> vendorList = vendorService.findAll();
//        2. 모델에 데이터 등록하기
        model.addAttribute("vendorList", vendorList);
//        3. 뷰 페이지 설정하기
        return "fina/vendorList";
    }

    @GetMapping("/edit/{vendorId}")
    public String editVendor(@PathVariable("vendorId") int vendorId, Model model) {
//        1. 수정할 데이터 가져오기
        Vendor vendorEntity = vendorService.findById(vendorId);
//        2. 모델에 데이터 등록하기
        model.addAttribute("vendor", vendorEntity);
//        3. 뷰 페이지 설정하기
        return "fina/vendorEdit";
    }

    @PostMapping("/update")
    public String updateVendor(@ModelAttribute Vendor vendor) {

    }


}
