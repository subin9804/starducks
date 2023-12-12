package org.kosta.starducks.fina.controller;

import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.dto.StoreDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/fina/store")
public class StoreController {

    /**
     * 지점 추가
     *
     * @return
     */
    @GetMapping("new")
    public String newStoreForm() {
        return "fina/branchAdd";
    }

    /**
     * 지점 추가 폼 데이터 받기
     *
     * @return
     */
    @PostMapping("/create")
    public String createStore(StoreDTO storeDTO) {
        log.info("storeDTO.toString() ==> " + storeDTO.toString());
        return "";
    }

    @GetMapping("/list")
    public String showStoreList() {
        return "fina/storeList";
    }
}
