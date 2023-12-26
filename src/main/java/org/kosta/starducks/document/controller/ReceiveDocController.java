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
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.hr.service.EmpFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/document/receiveDoc")
@RequiredArgsConstructor
public class ReceiveDocController {
    private final DocFormRepository docFormRepository;
    private final DocumentRepository documentRepository;
    private final ApprovalRepository approvalRepository;
    private final EmpRepository empRepository;

    private final EmpFileService fileService;
    private final DocumentService documentService;

    /**
     * 결재 수신 리스트 페이지
     */
    @GetMapping
    public String docReceiveList(Model model,
                                 Principal principal,
                                 @PageableDefault(page = 0, size = 5, sort = "docId", direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        Long empId = Long.parseLong(principal.getName()); //로그인 한 사원 번호

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

        // 검색된 게 아무것도 없을 때 페이지 번호가 1이 보이게 설정
        if (totalPages == 0) {
            endPage = 1;
        }

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
                                 Model model,
                                 Principal principal) {

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        documentRepository.findById(docId)
                .ifPresent(document -> {
                    //기존에 저장했던 document 정보
                    model.addAttribute("document", document);

                    //화면에 전달할 docWriter : 기안자(문서 작성자) : 저장된 docWriter
                    model.addAttribute("docWriter", document.getDocWriter());

                    //화면에 전달할 docWriterStamp
                    String stamp = fileService.getFileUrl(document.getDocWriter().getEmpId(), "stamp");
                    model.addAttribute("docWriterStamp", stamp);
                });

        //결재 도장 칸에 전달할 결재 데이터 apv1, 2, 결재 도장 데이터 apv1, 2 Stamp
        approvalRepository.findByApvStepAndDocument_DocId(1, docId)
                .ifPresent(apv1 -> {
                    //결재 데이터 apv1
                    model.addAttribute("apv1", apv1);

                    //결재 도장 데이터 apv1Stamp
                    String apv1Stamp = fileService.getFileUrl(apv1.getApvEmp().getEmpId(), "stamp");
                    model.addAttribute("apv1Stamp", apv1Stamp);
                });

        approvalRepository.findByApvStepAndDocument_DocId(2, docId)
                .ifPresent(apv2 -> {
                    //결재 데이터 apv2
                    model.addAttribute("apv2", apv2);

                    //결재 도장 데이터 apv2Stamp
                    String apv2Stamp = fileService.getFileUrl(apv2.getApvEmp().getEmpId(), "stamp");
                    model.addAttribute("apv2Stamp", apv2Stamp);
                });

        //화면에 전달할 refEmpNames
        String refEmpNames = documentService.getRefEmpNamesByDocId(docId);
        model.addAttribute("refEmpNames", refEmpNames);

        Long empId = Long.parseLong(principal.getName()); //로그인 한 사원 번호
        approvalRepository.findByDocument_DocIdAndApvEmp_EmpId(docId, empId)
                .ifPresent(approval -> {
                    //myApproval: 현재 문서의 내 결재 상태
                    model.addAttribute("myApproval", approval.getApvStatus().toString());

                    //readyApv: 내가 결재 1단계면 true, 혹은 결재 2단계라면 내 전 결재단계가 완료(승인)일 때 true
                    model.addAttribute("readyApv",
                            approval.getApvStep() == 1
                                    || (approval.getApvStep() == 2
                                    && approvalRepository.findByApvStepAndDocument_DocId(1, docId).get()
                                    .getApvStatus() == ApvStatus.APPROVED));
                });

        return "document/receiveDoc/docDetail";
    }

    /**
     * 수신 문서 결재 페이지
     */
    @GetMapping("/{formNameEn}/{docId}/apv")
    public String createApv(@PathVariable(name = "formNameEn") String formNameEn,
                                 @PathVariable(name = "docId") Long docId,
                                 Model model,
                            Principal principal) {

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        documentRepository.findById(docId)
                .ifPresent(document -> {
                    //기존에 저장했던 document 정보
                    model.addAttribute("document", document);

                    //화면에 전달할 docWriter : 기안자(문서 작성자) : 저장된 docWriter
                    model.addAttribute("docWriter", document.getDocWriter());

                    //화면에 전달할 docWriterStamp
                    String stamp = fileService.getFileUrl(document.getDocWriter().getEmpId(), "stamp");
                    model.addAttribute("docWriterStamp", stamp);
                });

        //결재 도장 칸에 전달할 결재 데이터 apv1, 2, 결재 도장 데이터 apv1, 2 Stamp
        approvalRepository.findByApvStepAndDocument_DocId(1, docId)
                .ifPresent(apv1 -> {
                    //결재 데이터 apv1
                    model.addAttribute("apv1", apv1);

                    //결재 도장 데이터 apv1Stamp
                    String apv1Stamp = fileService.getFileUrl(apv1.getApvEmp().getEmpId(), "stamp");
                    model.addAttribute("apv1Stamp", apv1Stamp);
                });

        approvalRepository.findByApvStepAndDocument_DocId(2, docId)
                .ifPresent(apv2 -> {
                    //결재 데이터 apv2
                    model.addAttribute("apv2", apv2);

                    //결재 도장 데이터 apv2Stamp
                    String apv2Stamp = fileService.getFileUrl(apv2.getApvEmp().getEmpId(), "stamp");
                    model.addAttribute("apv2Stamp", apv2Stamp);
                });

        //화면에 전달할 refEmpNames
        String refEmpNames = documentService.getRefEmpNamesByDocId(docId);
        model.addAttribute("refEmpNames", refEmpNames);

        Long empId = Long.parseLong(principal.getName()); //로그인 한 사원 번호
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
                            Model model,
                            Principal principal) {
//        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapprovalㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+approval);
        Long empId = Long.parseLong(principal.getName()); //로그인 한 사원 번호

        //document 생성 시 만들어진 apvState, apvComment, apvDate가 빈 approval 객체에 값 저장
        Approval existingApv = documentService.saveApproval(approval, docId, empId);

        //ApvState에 따라 DocState 변경
        documentService.updateDocStatusByApv(approval, existingApv, docId);

        return "redirect:/document/receiveDoc/" + formNameEn + "/" + docId;
    }
}
