package org.kosta.starducks.document.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.entity.*;
import org.kosta.starducks.document.repository.ApprovalRepository;
import org.kosta.starducks.document.repository.DocumentRepository;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.document.repository.RefEmpRepository;
import org.kosta.starducks.document.service.DocumentService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/document/createDoc")
@RequiredArgsConstructor
public class CreateDocController {
    private final DocumentService documentService;

    private final DocumentRepository documentRepository;
    private final DocFormRepository docFormRepository;
    private final EmpRepository empRepository;

    /**
     * 작성 문서 종류 리스트 페이지
     */
    @GetMapping
    public String docFormList(Model model) {
        List<DocForm> docFormList = docFormRepository.findAll();
        model.addAttribute("docForms", docFormList);

        return "document/createDoc/docFormList";
    }

    /**
     * 문서 작성 페이지
     */
    @GetMapping("/{formNameEn}")
    public String createDocument(@PathVariable(name = "formNameEn") String formNameEn, Model model) {
        //라디오로 입력 받을 apvEmpId1,2
        Long apvEmpId1 = null, apvEmpId2 = null;
        model.addAttribute("apvEmpId1", apvEmpId1);
        model.addAttribute("apvEmpId2", apvEmpId2);

        //멀티 체크박스로 입력 받을 refEmpIdList
        List<Long> refEmpIdList = null;
        model.addAttribute("refEmpIdList", refEmpIdList);

        //사원찾기에 사용될 emps
        List<Employee> emps = empRepository.findAll();
        model.addAttribute("emps", emps);

        //입력 받을 document 객체
        model.addAttribute("document", new Document());

        //화면에 전달할 docForm 정보 객체 : PathVariable 정보
        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        //화면에 전달할 empName : 로그인 한 사원 : 기안자(문서 작성자)
        String empName = "홍길동"; //로그인한 사원 이름
        model.addAttribute("empName", empName);

        return "document/createDoc/" + formNameEn;
    }

    /**
     * 문서 수정 페이지 (submit 이력 있는 경우 - 임시저장, 상신 모두 포함)
     */
    @GetMapping("/{formNameEn}/{docId}")
    public String updateDocument(@PathVariable(name = "formNameEn") String formNameEn,
                                 @PathVariable(name = "docId") Long docId,
                                 Model model) {

        //기존에 선택했던 apvEmpId1,2 라디오 정보
        List<Long> apvEmpIdList = documentService.getApvEmpIdsByDocId(docId);
        int i = 1;
        for (Long apvEmpId : apvEmpIdList) {
            model.addAttribute("apvEmpId" + i, apvEmpId);
        }

        //기존에 선택했던 refEmpIdList 멀티 체크박스 정보
        List<Long> refEmpIdList = documentService.getRefEmpIdsByDocId(docId);
        model.addAttribute("refEmpIdList", refEmpIdList);

        //사원찾기에 사용될 emps
        List<Employee> emps = empRepository.findAll();
        model.addAttribute("emps", emps);

        //기존에 저장했던 document 정보
        documentRepository.findById(docId)
                .ifPresent(document -> model.addAttribute("document", document));

        //화면에 전달할 docForm 정보 객체 : PathVariable 정보
        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        //화면에 전달할 empName : 로그인 한 사원 : 기안자(문서 작성자)
        String empName = "홍길동"; //로그인한 사원 이름
        model.addAttribute("empName", empName);

        return "document/createDoc/" + formNameEn;
    }

    /**
     * 문서 작성 상신 처리 - 첫 submit이 상신 - /{formNameEn} 에서 진입
     */
    @PostMapping("/{formNameEn}")
    public String submitDocument(@PathVariable(name = "formNameEn") String formNameEn,
                                 @ModelAttribute(name = "document") Document document,
                                 @RequestParam(name = "apvEmpId1") Long apvEmpId1,
                                 @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2, //2차 결재자는 없을 수 있음 - 화면에서 유효성 처리
                                 @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapvEmpId1ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+apvEmpId1);
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapvEmpId2ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+apvEmpId2);
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡrefEmpIdListㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+refEmpIdList);

        //Document 객체 정보 저장 : document, apvEmpIdList, refEmpIdList
        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
        Document savedDoc = documentService.saveDocumentAndApvAndRef(document, apvEmpIdList, refEmpIdList);

        redirectAttributes.addAttribute("docId", savedDoc.getDocId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/document/submitDoc/" + formNameEn + "/{docId}";
    }


    /**
     * 문서 작성 상신 처리 - submit 처음 아님 (임시저장 이력 있는 경우, 수정하는 경우) - /{formNameEn}/{docId} 에서 진입
     */
    @PostMapping("/{formNameEn}/{docId}")
    public String submitDocument2(@PathVariable(name = "formNameEn") String formNameEn,
                                  @PathVariable(name = "docId") Long docId,
                                  Document document,
                                  @RequestParam(name = "apvEmpId1") Long apvEmpId1,
                                  @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2, //2차 결재자는 없을 수 있음 - 화면에서 유효성 처리
                                  @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
                                  RedirectAttributes redirectAttributes) {

        List<Long> apvEmpIds = Arrays.asList(apvEmpId1, apvEmpId2);
        documentService.updateDocumentAndApvAndRef(docId, document, apvEmpIds, refEmpIdList);

        redirectAttributes.addAttribute("status", true);
        return "redirect:/document/submitDoc/" + formNameEn + "/" + docId;
    }

    /**
     * 문서 작성 임시 저장 처리 - 첫 submit이 임시저장 - /{formNameEn} 에서 진입
     */
    @PostMapping("/temp")
    public String submitDocumentTemp(Document document,
                                  @RequestParam(name = "apvEmpId1", required = false) Long apvEmpId1, //임시 저장은 값 없어도 됨
                                  @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2,
                                  @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
                                  RedirectAttributes redirectAttributes) {

        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
        refEmpIdList = refEmpIdList != null ? refEmpIdList : Collections.emptyList();
        Document savedDoc = documentService.tempDocumentAndApvAndRef(document, apvEmpIdList, refEmpIdList);

        String formCode = savedDoc.getDocForm().getFormCode();

        String formNameEn = docFormRepository.findByFormCode(formCode)
                .map(DocForm::getFormNameEn)
                .orElse(null);

        Long docId = savedDoc.getDocId();

        redirectAttributes.addAttribute("tmpStatus", true);
        return "redirect:/document/createDoc/" + formNameEn + "/" + docId;
    }

    /**
     * 문서 작성 임시 저장 처리 - 임시저장 submit 처음 아님 - /{formNameEn}/{docId} 에서 진입
     */
    @PostMapping("/temp2")
    public String submitDocumentTemp2(Document document,
//                                      @RequestParam(name = "document.docId", required = false) Long docId,
                                      @RequestParam(name = "apvEmpId1", required = false) Long apvEmpId1, //임시 저장은 값 없어도 됨
                                      @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2,
                                      @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
                                      RedirectAttributes redirectAttributes) {

        Long docId = document.getDocId();

        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
        refEmpIdList = refEmpIdList != null ? refEmpIdList : Collections.emptyList();
        Document savedDoc = documentService.temp2DocumentAndApvAndRef(docId, document, apvEmpIdList, refEmpIdList);

        String formCode = savedDoc.getDocForm().getFormCode();

        String formNameEn = docFormRepository.findByFormCode(formCode)
                .map(DocForm::getFormNameEn)
                .orElse(null);

        redirectAttributes.addAttribute("tmpStatus2", true);
        return "redirect:/document/createDoc/" + formNameEn + "/" + docId;
    }
}

