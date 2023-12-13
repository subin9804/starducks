package org.kosta.starducks.fina.controller;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.dto.StoreDTO;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.fina.entity.StoreManager;
import org.kosta.starducks.fina.entity.StoreOperationalYn;
import org.kosta.starducks.fina.repository.StoreRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/fina/store")
@RequiredArgsConstructor
@ToString
public class StoreController {

    private final StoreRepository storeRepository;
    private final EmpRepository empRepository;

    /**
     * 지점 추가
     *
     * @return
     */
    @GetMapping("new")
    public String newStoreForm(Model m) {

        List<Employee> byPosition = empRepository.findByPosition(Position.ROLE_STOREMANAGER);
        List<String> names = byPosition.stream()
                .map(Employee::getEmpName) // Employee 클래스에 getName() 메서드가 있어야 합니다.
                .collect(Collectors.toList());

        m.addAttribute("emps",names);



        return "fina/storeAdd";
    }

    /**
     * 지점 추가 폼 데이터 받기
     *
     * @return
     */
    @PostMapping("/create")
    public String createStore(StoreDTO storeDTO) {
        log.info("storeDTO.toString() ==> " + storeDTO.toString());
//        1. DTO를 엔티티로 변환
        Store store = storeDTO.toEntity();
        log.info("DTO가 엔티티로 잘 변환되는지 확인 출력" + store.toString());
//        2. 리파지터리로 엔티티를 DB에 저장
        Store saved = storeRepository.save(store);
        log.info("store이 DB에 잘 저장되는지 확인 출력" + saved.toString());
        System.out.println("store이 DB에 잘 저장되는지 확인 출력2" + saved.toString());
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
        Store storeEntity = storeRepository.findById(storeNo).orElse(null);
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
    public String showStoreList(Model model) {
//        1. 모든 데이터 가져오기
        ArrayList<Store> storeList = (ArrayList<Store>) storeRepository.findAll();
//        2. 모델에 데이터 등록하기
        model.addAttribute("storeList", storeList);
//        3. 뷰 페이지 설정하기
        return "fina/storeList";
    }

    /**
     * 수정하기
     *
     * @param storeNo
     * @param model
     * @return
     */
    @GetMapping("edit/{storeNo}")
    public String editStore(@PathVariable("storeNo") Long storeNo, Model model) {
//        1. 수정할 데이터 가져오기
        Store storeEntity = storeRepository.findById(storeNo).orElse(null);
//        2. 모델에 데이터 등록하기
        model.addAttribute("store", storeEntity);

        // 드롭다운 목록에 필요한 storeManager 값을 모델에 추가
        List<StoreManager> storeManagers = Arrays.asList(StoreManager.values());
        model.addAttribute("storeManagers", storeManagers);

        List<StoreOperationalYn> storeOperationalYn = Arrays.asList(StoreOperationalYn.values());
        model.addAttribute("storeOperationalYn", storeOperationalYn);

//        3. 뷰 페이지 설정
        return "fina/storeEdit";
    }

    @PostMapping("/update")
    public String updateStore(StoreDTO storeDTO) {
        log.info("수정 데이터 잘 받았니?? ==> " + storeDTO.toString());
//        1. DTO를 엔티티로 변환하기
        Store storeEntity = storeDTO.toEntity();
        log.info("엔티티로 잘 변환했니??? ==> " + storeEntity.toString());
//        2. 엔티티를 DB에 저장하기
        Store target = storeRepository.findById(storeEntity.getStoreNo()).orElse(null);
        if (target != null) {
            storeRepository.save(storeEntity);
        }
//        3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/fina/store/single/" + storeEntity.getStoreNo();
    }

    @GetMapping("delete/{storeNo}")
    public String deleteStore(@PathVariable("storeNo") Long storeNo, RedirectAttributes rttr) {
        log.info("삭제 요청 들어왔는지 확인");
//        1. 삭제할 대상 가져오기
        Store storeTarget = storeRepository.findById(storeNo).orElse(null);
        log.info("storeTarget에 데이터 있는지 확인 ==> " + storeTarget.toString());
//        2. 대상 엔티티 삭제하기
        if(storeTarget != null) {
            storeRepository.delete(storeTarget);
            rttr.addAttribute("msg", "삭제가 완료되었습니다.");
        }
//        3. 결과 페이지로 리다이렉트하기
        return "redirect:/fina/store/list";
    }


}
