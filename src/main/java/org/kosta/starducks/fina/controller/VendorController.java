package org.kosta.starducks.fina.controller;

import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.dto.VendorAndProductDTO;
import org.kosta.starducks.fina.entity.ContractStatus;
import org.kosta.starducks.fina.entity.VendorBusinessSector;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
//        log.info("여기는 컨트롤러");
        VendorAndProductDTO vendorAndProductDTO = new VendorAndProductDTO();

        model.addAttribute("vendor", new VendorAndProductDTO());

        model.addAttribute("vendorAndProductDTO", vendorAndProductDTO);
        model.addAttribute("businessSectors", VendorBusinessSector.values());
        model.addAttribute("contractStatus", ContractStatus.values());
        return "fina/vendorAdd";
    }

    /**
     * DTO를 엔티티로 변환
     */
    private Vendor VendorEntity(VendorAndProductDTO vendorAndProductDTO) {
        return modelMapper.map(vendorAndProductDTO, Vendor.class);
    }

    /**
     *
     * 거래처 추가 폼 데이터 받기
     * @param vendorAndProductDTO
     * @return
     */
    @PostMapping("/create")
    public String newVendor(VendorAndProductDTO vendorAndProductDTO) {
//        log.info("vendorAndProductDTO.toString() ==> " + vendorAndProductDTO.toString());
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
//        log.info("vendorId를 잘 받았는지 확인 ==> " + vendorId);
//        1. id를 조회해 데이터 가져오기
        Vendor vendorEntity = vendorService.findById(vendorId);
//        2. 모델에 데이터 등록하기
        model.addAttribute("vendor", vendorEntity);
        model.addAttribute("businessSectors", VendorBusinessSector.values());
        model.addAttribute("contractStatus", ContractStatus.values());
//        3. 뷰 페이지 반환하기
        return "fina/vendorDetail";
    }

    /**
     * 거래처 전체 조회
     */
    @GetMapping("/list")
    public String showVendorList(Model model,
                                 @PageableDefault(page = 0, size = 5, sort = "vendorId", direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {

        Page<Vendor> vendors = null;

        // 검색 키워드가 없으면 전체글을 페이저블 처리해서 보여주고, 키워드가 있으면 키워드에 맞게 글을 필터링하고, 리스트를 페이저블 처리해준다
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            vendors = vendorService.vendorSearchList(searchKeyword, pageable);
        } else {
            vendors = vendorService.getAllVendors(pageable);
        }

//        페이지 블럭 처리
        int nowPage = vendors.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, vendors.getTotalPages());
        int totalPages = vendors.getTotalPages();

        if (totalPages == 0) {
            endPage = 1;
        }

        model.addAttribute("vendors", vendors);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        List<Vendor> vendorList = vendorService.findAll();
        model.addAttribute("vendorList", vendorList);
        return "fina/vendorList";
    }

    @GetMapping("/edit/{vendorId}")
    public String editVendor(@PathVariable("vendorId") int vendorId, Model model) {
//        1. 수정할 데이터 가져오기
        Vendor vendor = vendorService.findById(vendorId);

        // 2. 업종 정보가 없는 경우 초기화
        if (vendor.getVendorBusinessSector() == null) {
            vendor.setVendorBusinessSector(VendorBusinessSector.COFFEEBEANSUPPLIERS);
        }
//        3. 모델에 데이터 등록하기

        model.addAttribute("vendor", vendor);
        model.addAttribute("businessSectors", VendorBusinessSector.values());   // 업종
        model.addAttribute("contractStatus", ContractStatus.values());  // 계약 상태
//        4. 뷰 페이지 설정하기
        return "fina/vendorEdit";
    }


    /**
     * DTO를 엔티티로 변환
     */
//    private Vendor VendorEntity(VendorAndProductDTO vendorAndProductDTO) {
//        return modelMapper.map(vendorAndProductDTO, Vendor.class);
//    }

    @PostMapping("/update")
    public String updateVendor(VendorAndProductDTO vendorAndProductDTO) {   // 매개변수로 DTO 받아오기
        vendorService.updateVendor(vendorAndProductDTO);
        return "redirect:/fina/vendor/single/" + vendorAndProductDTO.getVendorId();
    }

    /**
     * vendorId가 총무부의 Product의 FK라 삭제 불가
     */
//    @GetMapping("/delete/{vendorId}")
//    public String deleteVendor(@PathVariable("vendorId") int vendorId, RedirectAttributes rttr) {
//        vendorService.deleteVendor(vendorId, rttr);
//        return "redirect:/fina/vendor/list";
//    }
}
