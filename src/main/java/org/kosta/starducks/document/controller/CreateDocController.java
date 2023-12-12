package org.kosta.starducks.document.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.menus.MenuService;
import org.kosta.starducks.document.entity.DocForm;
import org.kosta.starducks.document.entity.DocStatus;
import org.kosta.starducks.document.entity.Document;
import org.kosta.starducks.document.repository.CreateDocRepository;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.document.service.CreateDocService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/createDoc")
@RequiredArgsConstructor
public class CreateDocController {
    private final CreateDocService createDocService;

    private final CreateDocRepository createDocRepository;
    private final DocFormRepository docFormRepository;
    private final EmpRepository empRepository;

    private final HttpServletRequest request;

    /**
     * 작성 문서 종류 리스트 페이지
     */
    @GetMapping
    public String docFormList(Model model) {
        MenuService.commonProcess(request, model, "document"); //getMapping에 넣어주기

        List<DocForm> docFormList = docFormRepository.findAll();
        model.addAttribute("docForms", docFormList);

        return "document/createDoc/docFormList";
    }

    /**
     * 문서 상세 페이지
     */
    @GetMapping("/{formNameEn}/{docId}")
    public String documentDetail(@PathVariable(name = "formNameEn") String formNameEn,
                                 @PathVariable(name = "docId") Long docId,
                                 Model model) {
        MenuService.commonProcess(request, model, "document");

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        createDocService.getDocumentById(docId)
                .ifPresent(document -> model.addAttribute("document", document));

        return "document/createDoc/" + formNameEn;
    }

    /**
     * 문서 작성 페이지
     */
    @GetMapping("/{formNameEn}")
    public String createDocument(@PathVariable(name = "formNameEn") String formNameEn, Model model) {
        MenuService.commonProcess(request, model, "document");

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        Long docId = 1L;
        createDocService.getDocumentById(docId)
                .ifPresent(document -> model.addAttribute("document", document));

        return "document/createDoc/" + formNameEn;
    }

    /**
     * 문서 작성 상신 처리 - 첫 submit
     */
    @PostMapping("/{formNameEn}")
    public String submitDocument(@PathVariable(name = "formNameEn") String formNameEn,
                                 Document document,
                                 RedirectAttributes redirectAttributes) {
        // Employee 엔티티에서 empId가 1L인 인스턴스를 가져오기
        Long empId = 1L; //로그인한 사원 정보
        Employee docWriter = empRepository.findById(empId).orElse(null);
        // document 엔티티에 docWriter 설정
        document.setDocWriter(docWriter);

        document.setDocStatus(DocStatus.PENDING_DOC);
        Document savedDoc = createDocRepository.save(document);

        redirectAttributes.addAttribute("docId", savedDoc.getDocId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/createDoc/" + formNameEn + "/{docId}";
    }

    //아마 안쓸것같기도 ..
    // 돈 크라이... 나진...
    /**
     * 문서 작성 상신 처리 - 임시저장 이력 있는 경우
     */
    @PostMapping("/{formNameEn}/{docId}")
    public String submitDocument2(@PathVariable(name = "formNameEn") String formNameEn,
                                  @PathVariable(name = "docId") Long docId,
                                  Document document,
                                  RedirectAttributes redirectAttributes) {
        // Employee 엔티티에서 empId가 1L인 인스턴스를 가져오기
        Long empId = 1L; //로그인한 사원 정보
        Employee docWriter = empRepository.findById(empId).orElse(null);
        // document 엔티티에 docWriter 설정
        document.setDocWriter(docWriter);

        document.setDocStatus(DocStatus.PENDING_DOC);
        Document savedDoc = createDocRepository.save(document);

        redirectAttributes.addAttribute("status", true);
        return "redirect:/createDoc/" + formNameEn + "/" + docId;
    }

    /**
     * 문서 작성 임시 저장 처리
     */
    @PostMapping("/{formNameEn}/{docId}/temp")
    public String submitDraftTemp(@PathVariable(name = "formNameEn") String formNameEn,
                                  @PathVariable(name = "docId") Long docId,
                                  Document document,
                                  RedirectAttributes redirectAttributes) {
        // Employee 엔티티에서 empId가 1L인 인스턴스를 가져오기
        Long empId = 1L; //로그인한 사원 정보
        Employee docWriter = empRepository.findById(empId).orElse(null);
        // document 엔티티에 docWriter 설정
        document.setDocWriter(docWriter);

        document.setDocStatus(DocStatus.TEMP_STORED);
        Document savedDoc = createDocRepository.save(document);

        redirectAttributes.addAttribute("tmpStatus", true);
        return "redirect:/createDoc/" + formNameEn + "/" + docId;
    }
}
