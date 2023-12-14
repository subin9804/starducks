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
        return "fina/storeAdd";
    }

    /**
     * 지점 추가 폼 데이터 받기
     *
     * @return
     */
    @PostMapping("/create")
    public String createStore(@ModelAttribute Store store, @RequestParam("storeManager") String storeManager) {
        Employee storeEmpName = empRepository.findByEmpName(storeManager);
        store.setEmployee(storeEmpName);
        log.info(storeEmpName.getEmpName());
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
        log.info("storeNo ==> " + storeNo); // storeNo를 잘 받았는지 확인하는 로그
//        1. id를 조회해 데이터 가져오기
        Store storeEntity = storeService.findById(storeNo);
//        2. 모델에 데이터 등록하기
        model.addAttribute("store", storeEntity);
//        3. 뷰 페이지 반환하기
        return "fina/storeDetail";
    }



    /**
     * 지점 목록 조회
     *
     * @return
     */
    @GetMapping("/list")
    public String showStoreList(Model model, @PageableDefault(page = 0,size = 3,sort = "storeNo", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Store> storeList = storeService.getAllStores(pageable);
        model.addAttribute("storeList", storeService.getAllStores(pageable));
        return "fina/storeList";
    }






    /**
     * 지점 목록 조회
     *
     * @return
     */
//    @GetMapping("/list")
//    public String showStoreList(Model model) {
//        List<Store> storeList = storeService.getAllStores();
//        model.addAttribute("storeList", storeList);
//        return "fina/storeList";
//    }

    /**
     * 수정하기
     *
     * @param storeNo
     * @param model
     * @return
     */
    @GetMapping("edit/{storeNo}")
    public String editStore(@PathVariable("storeNo") Long storeNo, Model model) {
        Store storeEntity = storeService.getStoreById(storeNo);
        model.addAttribute("store", storeEntity);

        List<StoreOperationalYn> storeOperationalYn = storeService.getAllStoreOperationalYn();
        model.addAttribute("storeOperationalYn", storeOperationalYn);

        List<String> allStoreManagers = storeService.getAllStoreManagers();
        model.addAttribute("storeManagers", allStoreManagers);
//        3. 뷰 페이지 설정
        return "fina/storeEdit";
    }


    @PostMapping("/update")
    public String updateStore(@ModelAttribute Store store, @RequestParam("storeManager") String storeManager) {
        Employee storeEmpName = empRepository.findByEmpName(storeManager);
        store.setEmployee(storeEmpName);
//        log.info("storeEmpName.getEmpName() ==> " + storeEmpName.getEmpName());
        storeService.updateStore(store);
        return "redirect:/fina/store/single/" + store.getStoreNo();
    }

    @GetMapping("delete/{storeNo}")
    public String deleteStore(@PathVariable("storeNo") Long storeNo, RedirectAttributes rttr) {
        storeService.deleteStore(storeNo, rttr);
        return "redirect:/fina/store/list";
    }


}