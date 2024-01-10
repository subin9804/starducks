package org.kosta.starducks.fina.controller;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.fina.entity.StoreOperationalYn;
import org.kosta.starducks.fina.repository.StoreRepository;
import org.kosta.starducks.fina.service.StoreService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/fina/store")
@RequiredArgsConstructor
@ToString
public class StoreController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;
    private final EmpRepository empRepository;

    /**
     * 지점 추가
     *
     * @return
     */
    @GetMapping("/new")
    public String newStoreForm(Model model) {
        List<String> storeAdd = storeService.getStoreManagersNames();
        model.addAttribute("storeManager", storeAdd);
//        우편번호
        model.addAttribute("store", new Store());
        return "fina/storeAdd";
    }

    /**
     * 지점 추가 폼 데이터 받기
     *
     * @return
     */
    @PostMapping("/create")
    public String createStore(@ModelAttribute("store") Store store, @RequestParam("storeManager") String storeManager) {
        Employee storeEmpName = empRepository.findByEmpName(storeManager);
        store.setEmployee(storeEmpName);
//        log.info(storeEmpName.getEmpName());
        storeService.createStore(store);

        return "redirect:/fina/store/list";
    }

    /**
     * 지점 단일 조회
     *
     * @param storeNo
     * @param model
     * @return
     */
    @GetMapping("/single/{storeNo}")
    public String showSingleStore(@PathVariable("storeNo") Long storeNo, Model model) {
//        log.info("storeNo ==> " + storeNo); // storeNo를 잘 받았는지 확인하는 로그
//        1. id를 조회해 데이터 가져오기
        Store storeEntity = storeService.findById(storeNo);
//        2. 모델에 데이터 등록하기
        model.addAttribute("store", storeEntity);
//        3. 뷰 페이지 반환하기
        return "fina/storeDetail";
    }

    /**
     * 지점 목록 조회(검색 + 페이징)
     *
     * @return
     */
    @GetMapping("/list")
    public String showStoreList(Model model,
                                @PageableDefault(page = 0, size = 5, sort = "storeNo", direction = Sort.Direction.DESC) Pageable pageable,
                                @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                Principal principal) {

        Page<Store> storeList = null;

        // 검색 키워드가 없으면 전체글을 페이저블 처리해서 보여주고, 키워드가 있으면 키워드에 맞게 글을 필터링하고, 리스트를 페이저블 처리해준다
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            storeList = storeService.storeSearchList(searchKeyword, pageable);
        } else {
            storeList = storeService.getAllStores(pageable);
        }


//        페이지 블럭 처리
        int nowPage = storeList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, storeList.getTotalPages());
        int totalPages = storeList.getTotalPages();

        // 검색된 게 아무것도 없을 때 페이지 번호가 1이 보이게 설정
        if (totalPages == 0) {
            endPage = 1;
        }

        // Principal에서 유저 정보 받아오기
        if (principal != null) {
            String loggedInEmpId = principal.getName();
            Employee employee = empRepository.findByEmpName(loggedInEmpId);
            model.addAttribute("loggedInEmpId", employee);
        }

        model.addAttribute("storeList", storeList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "fina/storeList";
    }

    /**
     * 수정하기
     *
     * @param storeNo
     * @param model
     * @return
     */
    @GetMapping("/edit/{storeNo}")
    public String editStore(@PathVariable("storeNo") Long storeNo, Model model) {
        Store storeEntity = storeService.getStoreById(storeNo);
        model.addAttribute("store", storeEntity);

        List<StoreOperationalYn> storeOperationalYn = storeService.getAllStoreOperationalYn();
        model.addAttribute("storeOperationalYn", storeOperationalYn);

        List<String> allStoreManagers = storeService.getAllStoreManagers();
        model.addAttribute("storeManagers", allStoreManagers);
//

        return "fina/storeEdit";
    }


    @PostMapping("/update")
    public String updateStore(@ModelAttribute Store store,
                              @RequestParam("storeManager") String storeManager) {

        // Employee 객체 설정
        Employee storeEmpName = empRepository.findByEmpName(storeManager);
        store.setEmployee(storeEmpName);

        storeService.updateStore(store);

//        log.info("storeEmpName.getEmpName() ==> " + storeEmpName.getEmpName());
        storeService.updateStore(store);
        return "redirect:/fina/store/single/" + store.getStoreNo();
    }

    @GetMapping("/delete/{storeNo}")
    public String deleteStore(@PathVariable("storeNo") Long storeNo, RedirectAttributes rttr) {
        storeService.deleteStore(storeNo, rttr);
        return "redirect:/fina/store/list";
    }
}