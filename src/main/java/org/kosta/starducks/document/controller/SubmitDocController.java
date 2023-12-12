//package org.kosta.starducks.document.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.kosta.starducks.document.service.CreateDocService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/document")
//@RequiredArgsConstructor
//public class SubmitDocController {
//    private final CreateDocService createDocService;
//    private final HttpServletRequest request;
//
//    /**
//     * 결재 상신 리스트 페이지
//     */
//    @GetMapping("/docSubmitList")
//    public String docSubmitList() {
////        MenuService.commonProcess(request, model, "create-doc"); getMapping에 넣어주기
//        return "document/createDoc/docSubmitList";
//    }
//}