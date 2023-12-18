package org.kosta.starducks.document.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.entity.Approval;
import org.kosta.starducks.document.entity.ApvStatus;
import org.kosta.starducks.document.entity.DocStatus;
import org.kosta.starducks.document.entity.Document;
import org.kosta.starducks.document.repository.ApprovalRepository;
import org.kosta.starducks.document.repository.DocumentRepository;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.document.service.DocumentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/document/receiveDoc")
@RequiredArgsConstructor
public class ReceiveDocController {
    private final DocFormRepository docFormRepository;
    private final DocumentRepository documentRepository;
    private final ApprovalRepository approvalRepository;

    private final DocumentService documentService;

    /**
     * 결재 수신 리스트 페이지
     */
    @GetMapping
    public String docReceiveList(Model model,
                                 @PageableDefault(page = 0, size = 5, sort = "docId", direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        Long empId = 1L; //로그인한 사원 번호

        Page<Document> documents = null;

        //검색 키워드가 없으면 전체글을 페이저블 처리해서 보여주고, 있으면 키워드에 맞게 글을 필터링(검색)처리까지 해서 보여줌
        if (searchKeyword == null) {
            documents = documentService.receiveDocuments(empId, pageable);
        } else {
            documents = documentService.searchReceiveDocuments(empId, searchKeyword, pageable);
        }

        // 페이지 조건 생성
        int nowPage = documents.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, documents.getTotalPages());
        int totalPages = documents.getTotalPages();

        model.addAttribute("documents", documents.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
        return "document/receiveDoc/docReceiveList";
    }

    /**
     * 수신 문서 상세 페이지
     */
    @GetMapping("/{formNameEn}/{docId}")
    public String documentDetail(@PathVariable(name = "formNameEn") String formNameEn,
                                 @PathVariable(name = "docId") Long docId,
                                 Model model) {

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        documentRepository.findByDocId(docId)
                .ifPresent(document -> model.addAttribute("document", document));

        approvalRepository.findByApvStepAndDocument_DocId(1, docId)
                .ifPresent(apv1 -> model.addAttribute("apv1", apv1));

        approvalRepository.findByApvStepAndDocument_DocId(2, docId)
                .ifPresent(apv2 -> model.addAttribute("apv2", apv2));

        return "document/receiveDoc/docDetail";
    }

    /**
     * 수신 문서 결재 페이지
     */
    @GetMapping("/{formNameEn}/{docId}/apv")
    public String createApv(@PathVariable(name = "formNameEn") String formNameEn,
                                 @PathVariable(name = "docId") Long docId,
                                 Model model) {

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        documentRepository.findByDocId(docId)
                .ifPresent(document -> model.addAttribute("document", document));

        Long empId = 1L; //로그인한 사원 정보
        approvalRepository.findByDocument_DocIdAndApvEmp_EmpId(docId, empId)
                .ifPresent(approval -> model.addAttribute("approval", approval));

        return "document/receiveDoc/docApproval";
    }

    /**
     * 수신 문서 결재 처리
     */
    @PostMapping("/{formNameEn}/{docId}/apv")
    public String summitApv(Approval approval,
                            @PathVariable(name = "formNameEn") String formNameEn,
                            @PathVariable(name = "docId") Long docId,
                            Model model) {
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapprovalㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+approval);
        Long empId = 1L; //로그인한 사원 정보
        Approval existingApv = approvalRepository.findByDocument_DocIdAndApvEmp_EmpId(docId, empId).get();
        existingApv.setApvStatus(approval.getApvStatus());
        existingApv.setApvComment(approval.getApvComment());
        existingApv.setApvDate(LocalDateTime.now());

        approvalRepository.save(existingApv);


        List<String> requiredStep2 = Arrays.asList("draft", "reqForVac"); //결재 두 단계인 양식명
        documentRepository.findById(docId).ifPresent(document -> {
            if (approval.getApvStatus() == ApvStatus.APPROVED && requiredStep2.contains(document.getDocForm().getFormNameEn())) {
                document.setDocStatus(existingApv.getApvStep() == 1 ? DocStatus.IN_PROGRESS : DocStatus.APPROVED_DOC);
            } else if (approval.getApvStatus() == ApvStatus.APPROVED) {
                //결재 한 단계인 경우는 1단계 승인 시 문서 최종 승인 상태로 변경
                document.setDocStatus(DocStatus.APPROVED_DOC);
            } else {
                document.setDocStatus(DocStatus.REJECTED_DOC);
            }
            documentRepository.save(document);
        });

        return "redirect:/document/receiveDoc/" + formNameEn + "/" + docId;
    }
}
