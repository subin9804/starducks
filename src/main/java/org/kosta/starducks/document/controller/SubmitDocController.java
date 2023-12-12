package org.kosta.starducks.document.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.menus.MenuService;
import org.kosta.starducks.document.entity.Document;
import org.kosta.starducks.document.repository.CreateDocRepository;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.document.service.CreateDocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/document/submitDoc")
@RequiredArgsConstructor
public class SubmitDocController {
    private final CreateDocService createDocService;

    private final DocFormRepository docFormRepository;
    private final CreateDocRepository createDocRepository;

    private final HttpServletRequest request;

    /**
     * 결재 상신 리스트 페이지
     */
    @GetMapping
    public String docSubmitList(Model model) {
        MenuService.commonProcess(request, model, "document");
        List<Document> documents = createDocRepository.findAll();
        model.addAttribute("documents", documents);
        return "document/submitDoc/docSubmitList";
    }

    /**
     * 상신 문서 상세 페이지
     */
    @GetMapping("/{formNameEn}/{docId}")
    public String documentDetail(@PathVariable(name = "formNameEn") String formNameEn,
                                 @PathVariable(name = "docId") Long docId,
                                 Model model) {
        MenuService.commonProcess(request, model, "document");

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        createDocRepository.findByDocId(docId)
                .ifPresent(document -> model.addAttribute("document", document));

        return "document/submitDoc/docDetail";
    }
}