package org.kosta.starducks.document.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.MenuService;
import org.kosta.starducks.document.service.CreateDocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/createDoc")
@RequiredArgsConstructor
public class CreateDocController {
    private final CreateDocService createDocService;
    private final HttpServletRequest request;

    /**
     * 작성 문서 종류 리스트 페이지
     */
    @GetMapping
    public String docFormList(Model model) {
//        MenuService.commonProcess(request, model, "document"); //getMapping에 넣어주기
        return "document/createDoc/docFormList";
    }

    /**
     * 업무 기안서 양식 작성 페이지
     */
    @GetMapping("/draft")
    public String draft(Model model) {
        MenuService.commonProcess(request, model, "document"); //getMapping에 넣어주기
        return "document/createDoc/draft";
    }

    //임시저장 및 상신 버튼을 처음 submit 할 때: post
    //임시저장 누른 후 버튼을 다시 누를 때(doc_id가 존재할 때): put
    /**
     * 업무 기안서 양식 작성 완료 페이지 - 첫 submit
     */
    @PostMapping("/draftSubmit")
    public String submitDraft() {
//        MenuService.commonProcess(request, model, "create-doc"); getMapping에 넣어주기
        return "redirect:/document/createDoc/docSubmitList";
    }

    /**
     * 업무 기안서 양식 작성 완료 페이지 - 임시저장 이력 있는 경우
     */
    @PutMapping("/draftSubmit2")
    public String submitDraft2() {
//        MenuService.commonProcess(request, model, "create-doc"); getMapping에 넣어주기
        return "redirect:/document/createDoc/docSubmitList";
    }

    /**
     * 업무 기안서 양식 임시 저장 완료 페이지 - 첫 submit
     */
    @PostMapping("/tempDraftSubmit")
    public String submitDraftTemp() {
//        MenuService.commonProcess(request, model, "create-doc"); getMapping에 넣어주기
        return "document/createDoc/tempDraftSuccess";
    }

    /**
     * 업무 기안서 양식 임시 저장 완료 페이지 - 임시저장 이력 있는 경우
     */
    @PutMapping("/tempDraftSubmit2")
    public String SubmitDraftTemp2() {
//        MenuService.commonProcess(request, model, "create-doc"); getMapping에 넣어주기
        return "document/createDoc/tempDraftSuccess2";
    }
}
