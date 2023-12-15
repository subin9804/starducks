package org.kosta.starducks.fina.controller;

import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.dto.VendorAndProductDTO;
import org.kosta.starducks.fina.entity.VendorBusinessSector;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String newVendorForm(Model model) {
        VendorAndProductDTO vendorAndProductDTO = new VendorAndProductDTO();
        model.addAttribute("vendorAndProductDTO", vendorAndProductDTO);
        model.addAttribute("businessSectors", VendorBusinessSector.values());
        return "fina/vendorAdd";
    }

//    @PostMapping("/new")
//    public String newVendor(VendorAndProductDTO vendorAndProductDTO) {
//        log.info("vendorAndProductDTO.toString() ==> " + vendorAndProductDTO.toString());
//        // 1. DTO를 엔티티로 변환
//        Vendor vendor = vendorandProductEntity(vendorAndProductDTO);
//
//        // 2. 리파지터리로 엔티티를 DB에 저장
//        vendorEntity vendorEntity = modelMapper.map(vendorAndProductDTO, vendorEntity.class);
//        )
//
//        return "redirect:/fina/vendor/list";
//    }

    //    목록조회
    @GetMapping()
    public String vendorList() {
        return "vendorList";
    }

}
