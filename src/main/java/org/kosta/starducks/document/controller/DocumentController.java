package org.kosta.starducks.document.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    /**
     * 결재함 홈
     */
    @GetMapping
    public String documentHome() {
        return "/document/docHome";
    }
}
