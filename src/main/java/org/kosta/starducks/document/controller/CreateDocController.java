package org.kosta.starducks.document.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.entity.*;
import org.kosta.starducks.document.repository.ApprovalRepository;
import org.kosta.starducks.document.repository.DocumentRepository;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.document.service.DocumentService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.hr.service.EmpFileService;
import org.kosta.starducks.hr.service.EmpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/document/createDoc")
@RequiredArgsConstructor
public class CreateDocController {
    private final DocumentService documentService;
    private final EmpFileService fileService;

    private final DocumentRepository documentRepository;
    private final DocFormRepository docFormRepository;
    private final ApprovalRepository approvalRepository;
    private final EmpRepository empRepository;
    private final EmpService empService;

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
    @GetMapping("/draft")
    public String createDocument(Principal p, Model model) {
        String formNameEn = "draft";

        //라디오로 입력 받을 apvEmpId1,2
        Long apvEmpId1 = null, apvEmpId2 = null;
        model.addAttribute("apvEmpId1", apvEmpId1);
        model.addAttribute("apvEmpId2", apvEmpId2);

        //멀티 체크박스로 입력 받을 refEmpIdList
        List<Long> refEmpIdList = null;
        model.addAttribute("refEmpIdList", refEmpIdList);

        //사원찾기에 사용될 emps - 재직 중인 사원만
        List<Employee> emps = empRepository.findAll()
                .stream()
                .filter(item -> item.isStatus() == false)
                .toList();
        model.addAttribute("emps", emps);

        //사원찾기에 사용될 프로필 이미지
        Map<Long, String> profiles = new HashMap();
        for(Employee emp : emps) {
            String profile = fileService.getFileUrl(emp.getEmpId(), "profile");
            profiles.put(emp.getEmpId(), profile);
        }
        model.addAttribute("profiles", profiles);

        //입력 받을 document 객체
        model.addAttribute("document", new Document());

        //화면에 전달할 expectedDocId
        model.addAttribute("expectedDocId", documentRepository.findAll().size() + 1);

        //화면에 전달할 docForm 정보 객체 : PathVariable 정보
        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        //화면에 전달할 docWriter : 로그인 한 사원 : 기안자(문서 작성자)
        Employee docWriter = empService.getEmp(Long.parseLong(p.getName()));
        model.addAttribute("docWriter", docWriter);

        //화면에 전달할 docWriterStamp
        String stamp = fileService.getFileUrl(docWriter.getEmpId(), "stamp");
        model.addAttribute("docWriterStamp", stamp);

        return "document/createDoc/" + formNameEn;
    }

    /**
     * 문서 수정 페이지 (submit 이력 있는 경우 - 임시저장, 상신 모두 포함)
     */
    @GetMapping("/draft/{docId}")
    public String updateDocument(@PathVariable(name = "docId") Long docId,
                                 Model model) {
        String formNameEn = "draft";

        //기존에 선택했던 apvEmpId1,2 라디오 정보
        List<Long> apvEmpIdList = documentService.getApvEmpIdsByDocId(docId);
        int i = 1;
        for (Long apvEmpId : apvEmpIdList) {
            model.addAttribute("apvEmpId" + i, apvEmpId);
            i++;
        }

        //기존에 선택했던 refEmpIdList 멀티 체크박스 정보
        List<Long> refEmpIdList = documentService.getRefEmpIdsByDocId(docId);
        model.addAttribute("refEmpIdList", refEmpIdList);

        //사원찾기에 사용될 emps - 재직 중인 사원만
        List<Employee> emps = empRepository.findAll()
                .stream()
                .filter(item -> item.isStatus() == false)
                .toList();
        model.addAttribute("emps", emps);

        //사원찾기에 사용될 프로필 이미지
        Map<Long, String> profiles = new HashMap();
        for(Employee emp : emps) {
            String profile = fileService.getFileUrl(emp.getEmpId(), "profile");
            profiles.put(emp.getEmpId(), profile);
        }
        model.addAttribute("profiles", profiles);

        //화면에 전달할 docForm 정보 객체 : PathVariable 정보
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
                    String apv1Stamp;
                    if (apv1.getApvEmp() != null) {
                        apv1Stamp = fileService.getFileUrl(apv1.getApvEmp().getEmpId(), "stamp");
                    } else {
                        apv1Stamp = null; //임시저장은 값이 없을 수 있음
                    }
                    model.addAttribute("apv1Stamp", apv1Stamp);
                });

        approvalRepository.findByApvStepAndDocument_DocId(2, docId)
                .ifPresent(apv2 -> {
                    //결재 데이터 apv2
                    model.addAttribute("apv2", apv2);

                    //결재 도장 데이터 apv2Stamp
                    String apv2Stamp;
                    if (apv2.getApvEmp() != null) {
                        apv2Stamp = fileService.getFileUrl(apv2.getApvEmp().getEmpId(), "stamp");
                    } else {
                        apv2Stamp = null; //임시저장은 값이 없을 수 있음
                    }
                    model.addAttribute("apv2Stamp", apv2Stamp);
                });

        return "document/createDoc/" + formNameEn;
    }

    /**
     * 문서 작성 상신 처리 - 첫 submit이 상신 - /{formNameEn} 에서 진입
     */

    @PostMapping("/draft")
    public String submitDocument(@ModelAttribute(name = "document") Document document,
                                 @RequestParam(name = "apvEmpId1") Long apvEmpId1,
                                 Principal principal,
                                 @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2, //2차 결재자는 없을 수 있음 - 화면에서 유효성 처리
                                 @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
                                 RedirectAttributes redirectAttributes) {


        Long empId = Long.parseLong(principal.getName()); //로그인 한 사원 번호

        //Document 객체 정보 저장 : document, apvEmpIdList, refEmpIdList
        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
        refEmpIdList = refEmpIdList != null ? refEmpIdList : Collections.emptyList();
//        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡrefEmpIdListㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+refEmpIdList);

        String code = "draft";
        Document savedDoc = documentService.saveDocumentAndApvAndRef(document, code, apvEmpIdList, refEmpIdList, empId);


        redirectAttributes.addAttribute("docId", savedDoc.getDocId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/document/submitDoc/" + code + "/{docId}";
    }


    /**
     * 문서 작성 상신 처리 - submit 처음 아님 (임시저장 이력 있는 경우, 수정하는 경우) - /{formNameEn}/{docId} 에서 진입
     */
    @PostMapping("/update")
    public String updateDocument(@RequestParam(name = "docId") Long docId,
                                  @ModelAttribute(name = "document") Document document,
                                  Principal principal,
                                  @RequestParam(name = "apvEmpId1") Long apvEmpId1,
                                  @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2, //2차 결재자는 없을 수 있음 - 화면에서 유효성 처리
                                  @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
                                  RedirectAttributes redirectAttributes) {
        String formNameEn = "draft";
        Long empId = Long.parseLong(principal.getName()); //로그인 한 사원 번호

        List<Long> apvEmpIds = Arrays.asList(apvEmpId1, apvEmpId2);
        refEmpIdList = refEmpIdList != null ? refEmpIdList : Collections.emptyList();
//        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡupdate refEmpIdListㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+refEmpIdList);
        documentService.updateDocumentAndApvAndRef(docId, document, apvEmpIds, refEmpIdList, empId);

        redirectAttributes.addAttribute("status", true);
        return "redirect:/document/submitDoc/" + formNameEn + "/" + docId;
    }

    /**
     * 문서 작성 임시 저장 처리 - 첫 submit이 임시저장 - /{formNameEn} 에서 진입
     */
    @PostMapping("/temp")
    public String submitDocumentTemp(@ModelAttribute(name = "document") Document document,
                                     Principal principal,
                                    @RequestParam(name = "apvEmpId1", required = false) Long apvEmpId1, //임시 저장은 값 없어도 됨
                                    @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2,
                                    @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
                                    RedirectAttributes redirectAttributes) {
        String formNameEn = "draft";
        Long empId = Long.parseLong(principal.getName()); //로그인 한 사원 번호

        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
        refEmpIdList = refEmpIdList != null ? refEmpIdList : Collections.emptyList();
        Document savedDoc = documentService.tempDocumentAndApvAndRef(document, apvEmpIdList, refEmpIdList, empId);

        Long docId = savedDoc.getDocId();

        redirectAttributes.addAttribute("tmpStatus", true);
        return "redirect:/document/createDoc/" + formNameEn + "/" + docId;
    }

    /**
     * 문서 작성 임시 저장 처리 - 임시저장 submit 처음 아님 - /{formNameEn}/{docId} 에서 진입
     */
    @PostMapping("/tempUpdate")
    public String updateDocumentTemp(@RequestParam(name = "docId") Long docId,
                                      @ModelAttribute(name = "document") Document document,
                                      Principal principal,
                                      @RequestParam(name = "apvEmpId1", required = false) Long apvEmpId1, //임시 저장은 값 없어도 됨
                                      @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2,
                                      @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
                                      RedirectAttributes redirectAttributes) {
        Long empId = Long.parseLong(principal.getName()); //로그인 한 사원 번호

        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
        refEmpIdList = refEmpIdList != null ? refEmpIdList : Collections.emptyList();
        Document savedDoc = documentService.temp2DocumentAndApvAndRef(docId, document, apvEmpIdList, refEmpIdList, empId);

        String formNameEn = savedDoc.getDocForm().getFormNameEn();

        redirectAttributes.addAttribute("tmpStatus2", true);
        return "redirect:/document/createDoc/" + formNameEn + "/" + docId;
    }
}

