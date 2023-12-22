package org.kosta.starducks.document.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.service.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    /**
     * 결재함 홈
     */
    @GetMapping
    public String documentHome(Model model) {
        Long empId = 1L; //로그인한 사원 번호

        model.addAttribute("receiveDocs", documentService.homeReceiveDocuments(empId));
        model.addAttribute("submitDocs", documentService.homeSubmitDocuments(empId));
        model.addAttribute("tempDocs", documentService.homeTempDocuments(empId));
        return "document/docHome";
    }
}
