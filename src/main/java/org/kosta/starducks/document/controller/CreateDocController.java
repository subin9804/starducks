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
import java.util.List;

@Controller
@RequestMapping("/document/createDoc")
@RequiredArgsConstructor
public class CreateDocController {
    private final DocumentService documentService;

    private final DocumentRepository documentRepository;
    private final DocFormRepository docFormRepository;
    private final EmpRepository empRepository;
    private final ApprovalRepository approvalRepository;
    private final RefEmpRepository refEmpRepository;

    private final HttpServletRequest request;

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
        Long apvEmpId1 = null, apvEmpId2 = null;
        model.addAttribute("apvEmpId1", apvEmpId1);
        model.addAttribute("apvEmpId2", apvEmpId2);
        List<Long> refEmpIdList = null;
        model.addAttribute("refEmpIdList", refEmpIdList);

        List<Employee> emps = empRepository.findAll();
        model.addAttribute("emps", emps);

        model.addAttribute("document", new Document());

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        String empName = "홍길동"; //로그인한 사원 이름
        model.addAttribute("empName", empName);

        List<Employee> employeeList = empRepository.findAll();
        model.addAttribute("employees", employeeList);
        return "document/createDoc/" + formNameEn;
    }

    /**
     * 문서 작성 상신 처리 - 첫 submit
     */
    @PostMapping("/{formNameEn}")
    public String submitDocument(@PathVariable(name = "formNameEn") String formNameEn,
                                 @ModelAttribute(name = "document") Document document,
                                 @RequestParam(name = "apvEmpId1") Long apvEmpId1,
                                 @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2, //2차 결재자는 없을 수 있음 - 화면에서 유효성 처리
                                 @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIds,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapvEmpId1ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+apvEmpId1);
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapvEmpId2ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+apvEmpId2);
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡrefEmpIdListㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+refEmpIds);

        List<Long> apvEmpIds = Arrays.asList(apvEmpId1, apvEmpId2);
        Document savedDoc = documentService.saveDocumentAndApvAndRef(document, apvEmpIds, refEmpIds);

        redirectAttributes.addAttribute("docId", savedDoc.getDocId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/document/submitDoc/" + formNameEn + "/{docId}";
    }

    /**
     * 문서 작성 중 (임시저장 한 경우), 수정 페이지
     */
    @GetMapping("/{formNameEn}/{docId}")
    public String createDocumentTemp(@PathVariable(name = "formNameEn") String formNameEn,
                                 @PathVariable(name = "docId") Long docId,
                                 Model model) {

        List<Long> apvEmpIds = documentService.getApvEmpIdsByDocId(docId);
        int i = 1;
        for (Long apvEmpId : apvEmpIds) {
            model.addAttribute("apvEmpId" + i, apvEmpId);
        }

        List<Long> refEmpIdList = documentService.getRefEmpIdsByDocId(docId);
        model.addAttribute("refEmpIdList", refEmpIdList);

        List<Employee> emps = empRepository.findAll();
        model.addAttribute("emps", emps);

        model.addAttribute("document", documentRepository.findById(docId));

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        String empName = "홍길동"; //로그인한 사원 이름
        model.addAttribute("empName", empName);

        List<Employee> employeeList = empRepository.findAll();
        model.addAttribute("employees", employeeList);

        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        documentRepository.findByDocId(docId)
                .ifPresent(document -> model.addAttribute("document", document));
        return "document/createDoc/" + formNameEn;
    }

    /**
     * 문서 작성 상신 처리 - 임시저장 이력 있는 경우, 수정하는 경우
     */
    @PostMapping("/{formNameEn}/{docId}")
    public String submitDocument2(@PathVariable(name = "formNameEn") String formNameEn,
                                  @PathVariable(name = "docId") Long docId,
                                  Document document,
                                  @RequestParam(name = "apvEmpId1") Long apvEmpId1,
                                  @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2, //2차 결재자는 없을 수 있음 - 화면에서 유효성 처리
                                  @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIds,
                                  RedirectAttributes redirectAttributes) {


        List<Long> apvEmpIds = Arrays.asList(apvEmpId1, apvEmpId2);
        documentService.updateDocumentAndApvAndRef(docId, document, apvEmpIds, refEmpIds);

//        documentRepository.findByDocId(docId);

        redirectAttributes.addAttribute("status", true);
        return "redirect:/document/submitDoc/" + formNameEn + "/" + docId;
    }

    /**
     * 문서 작성 임시 저장 처리
     */
    @PostMapping("/temp")
    public String submitDraftTemp(Document document,
                                  @RequestParam(name = "apvEmpId1", required = false) Long apvEmpId1, //임시 저장은 값 없어도 됨
                                  @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2,
                                  @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIds,
                                  RedirectAttributes redirectAttributes) {
        List<Long> apvEmpIds = Arrays.asList(apvEmpId1, apvEmpId2);
        Document savedDoc = documentService.tempDocumentAndApvAndRef(document, apvEmpIds, refEmpIds);

        String formCode = savedDoc.getDocForm().getFormCode();

        String formNameEn = docFormRepository.findByFormCode(formCode)
                .map(DocForm::getFormNameEn)
                .orElse(null);

        Long docId = savedDoc.getDocId();

        redirectAttributes.addAttribute("tmpStatus", true);
        return "redirect:/document/createDoc/" + formNameEn + "/" + docId;
    }
}
