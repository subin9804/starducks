package org.kosta.starducks.fina.controller;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.dto.StoreDTO;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.fina.repository.StoreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/fina/store")
@RequiredArgsConstructor
@ToString
public class StoreController {

    private final StoreRepository storeRepository;

    /**
     * 지점 추가
     *
     * @return
     */
    @GetMapping("new")
    public String newStoreForm() {
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

    // 지점 단일 조회
    @GetMapping("/single/{storeNo}")
    public String showSingleStore(@PathVariable("storeNo") Long storeNo) {
        log.info("storeNo ==> " + storeNo); // storeNo를 잘 받았는지 확인하는 로그
        return "";
    }

    /**
     * 지점 목록 조회
     * @return
     */
    @GetMapping("/list")
    public String showStoreList() {
        return "fina/storeList";
    }
}
