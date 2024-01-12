package org.kosta.starducks.document.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.document.entity.Document;
import org.kosta.starducks.document.entity.OrderItem;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.document.repository.DocumentRepository;
import org.kosta.starducks.document.repository.OrderItemRepository;
import org.kosta.starducks.document.service.DocumentService;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.service.ProductService;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.hr.service.EmpService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/document/createDoc")
@RequiredArgsConstructor
@Slf4j
public class COrderController {
    private final DocumentService documentService;

    private final DocumentRepository documentRepository;
    private final DocFormRepository docFormRepository;
    private final EmpRepository empRepository;
    private final EmpService empService;
    private final VendorService vendorService;
    private final ProductService productService;
    private final OrderItemRepository oiRepository;

    /**
     * 문서 작성 페이지
     */
    @GetMapping("/orderForm")
    public String createDocument(Principal p,
                                 Model model) {
        String formNameEn = "orderForm";

        //라디오로 입력 받을 apvEmpId1,2 및 vendor
        Long apvEmpId1 = null, apvEmpId2 = null;
        Integer selVendorId = null;
        model.addAttribute("apvEmpId1", apvEmpId1);
        model.addAttribute("apvEmpId2", apvEmpId2);
        model.addAttribute("selVendorId", selVendorId);

        //여러 개의 품목과 그에 매칭되는 인풋값 입력받을 model 추가
        //이중 리스트를 보내고 싶오..!


        //사원찾기에 사용될 emps
        List<Employee> emps = empRepository.findAll();
        model.addAttribute("emps", emps);

        //거래처 찾기에 이용될 vendors
        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);

        //입력 받을 document 객체
        model.addAttribute("document", new Document());
//
        //화면에 전달할 docForm 정보 객체 : PathVariable 정보
        docFormRepository.findByFormNameEn(formNameEn)
                .ifPresent(docForm -> model.addAttribute("docForm", docForm));

        //화면에 전달할 empName : 로그인 한 사원 : 기안자(문서 작성자)
        Employee emp = empService.getEmp(Long.parseLong(p.getName()));
        model.addAttribute("emp", emp);

        return "document/createDoc/" + formNameEn;
    }


    @GetMapping("/{vendorId}/products")
    public ResponseEntity<List<Product>> getProductsByVendorId(@PathVariable int vendorId) {
        List<Product> products = productService.getProductsByVendorId(vendorId);


        return ResponseEntity.ok(products);
    }


//    /**
//     * 문서 수정 페이지 (submit 이력 있는 경우 - 임시저장, 상신 모두 포함)
//     */
//    @GetMapping("/{docId}")
//    public String updateDocument(@PathVariable(name = "docId") Long docId,
//                                 Model model) {
//        String formNameEn = "draft";
//
//        //기존에 선택했던 apvEmpId1,2 라디오 정보
//        List<Long> apvEmpIdList = documentService.getApvEmpIdsByDocId(docId);
//        int i = 1;
//        for (Long apvEmpId : apvEmpIdList) {
//            model.addAttribute("apvEmpId" + i, apvEmpId);
//        }
//
//        //기존에 선택했던 refEmpIdList 멀티 체크박스 정보
//        List<Long> refEmpIdList = documentService.getRefEmpIdsByDocId(docId);
//        model.addAttribute("refEmpIdList", refEmpIdList);
//
//        //사원찾기에 사용될 emps
//        List<Employee> emps = empRepository.findAll();
//        model.addAttribute("emps", emps);
//
//        //기존에 저장했던 document 정보
//        documentRepository.findById(docId)
//                .ifPresent(document -> model.addAttribute("document", document));
//
//        //화면에 전달할 docForm 정보 객체 : PathVariable 정보
//        docFormRepository.findByFormNameEn(formNameEn)
//                .ifPresent(docForm -> model.addAttribute("docForm", docForm));
//
//        //화면에 전달할 empName : 로그인 한 사원 : 기안자(문서 작성자)
//        String empName = "홍길동"; //로그인한 사원 이름
//        model.addAttribute("empName", empName);
//
//        return "document/createDoc/" + formNameEn;
//    }


    /**
     * 문서 수정 페이지 (submit 이력 있는 경우 - 임시저장, 상신 모두 포함)
     */
//    @GetMapping("/orderForm/{docId}")
//    public String updateDocument(@PathVariable(name = "docId") Long docId,
//                                 Model model) {
//        String formNameEn = "orderForm";
//
//        //기존에 선택했던 apvEmpId1,2 라디오 정보
//        List<Long> apvEmpIdList = documentService.getApvEmpIdsByDocId(docId);
//        int i = 1;
//        for (Long apvEmpId : apvEmpIdList) {
//            model.addAttribute("apvEmpId" + i, (long)apvEmpId);
//            i++;
//        }
//
//
//        //사원찾기에 사용될 emps - 재직 중인 사원만
//        List<Employee> emps = empRepository.findAll()
//                .stream()
//                .filter(item -> item.isStatus() == false)
//                .toList();
//        model.addAttribute("emps", emps);
//
//        //사원찾기에 사용될 프로필 이미지
//        Map<Long, String> profiles = new HashMap();
//        for(Employee emp : emps) {
//            String profile = fileService.getFileUrl(emp.getEmpId(), "profile");
//            profiles.put(emp.getEmpId(), profile);
//        }
//        model.addAttribute("profiles", profiles);
//
//        //화면에 전달할 docForm 정보 객체 : PathVariable 정보
//        docFormRepository.findByFormNameEn(formNameEn)
//                .ifPresent(docForm -> model.addAttribute("docForm", docForm));
//
//        documentRepository.findById(docId)
//                .ifPresent(document -> {
//                    //기존에 저장했던 document 정보
//                    model.addAttribute("document", document);
//
//                    //화면에 전달할 docWriter : 기안자(문서 작성자) : 저장된 docWriter
//                    model.addAttribute("docWriter", document.getDocWriter());
//
//                    //화면에 전달할 docWriterStamp
//                    String stamp = fileService.getFileUrl(document.getDocWriter().getEmpId(), "stamp");
//                    model.addAttribute("docWriterStamp", stamp);
//                });
//
//        //결재 도장 칸에 전달할 결재 데이터 apv1, 2, 결재 도장 데이터 apv1, 2 Stamp
//        approvalRepository.findByApvStepAndDocument_DocId(1, docId)
//                .ifPresent(apv1 -> {
//                    //결재 데이터 apv1
//                    model.addAttribute("apv1", apv1);
//
//                    //결재 도장 데이터 apv1Stamp
//                    String apv1Stamp = fileService.getFileUrl(apv1.getApvEmp().getEmpId(), "stamp");
//                    model.addAttribute("apv1Stamp", apv1Stamp);
//                });
//
//        approvalRepository.findByApvStepAndDocument_DocId(2, docId)
//                .ifPresent(apv2 -> {
//                    //결재 데이터 apv2
//                    model.addAttribute("apv2", apv2);
//
//                    //결재 도장 데이터 apv2Stamp
//                    String apv2Stamp = fileService.getFileUrl(apv2.getApvEmp().getEmpId(), "stamp");
//                    model.addAttribute("apv2Stamp", apv2Stamp);
//                });
//
//        return "document/createDoc/" + formNameEn;
//    }

    /**
     * 문서 작성 상신 처리 - 첫 submit이 상신 - /{formNameEn} 에서 진입
     */
    @PostMapping("/orderForm")
    public String submitDocument(Principal p,
                                 @ModelAttribute(name = "document") Document document,
                                 @RequestParam(name = "apvEmpId1") Long apvEmpId1,
                                 @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2, //2차 결재자는 없을 수 있음 - 화면에서 유효성 처리
                                 @RequestParam(name = "selVendorId") int selVendorId,
                                 @RequestParam(name = "orderList") String orderList1,
                                 RedirectAttributes redirectAttributes) throws JsonProcessingException {
        String formNameEn = "orderForm";
        Long empId = Long.parseLong(p.getName()); //로그인 한 사원 번호

        //품목 저장 메서드
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode orderListNode = objectMapper.readTree(orderList1);
        List<OrderItem> orderItems = new ArrayList<>();

        //JsonNode에서 각 항목 추출
        for (JsonNode orderItemNode : orderListNode) {
            int productCode = orderItemNode.get("productCode").asInt();
            int quantity = orderItemNode.get("quantity").asInt();
            Product product = productService.getProduct((long) productCode).get();

            OrderItem orderItem = new OrderItem(product, quantity);
            orderItems.add(orderItem);
        }


        //Document 객체 정보 저장 : document, apvEmpIdList, refEmpIdList
        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
        Document savedDoc = documentService.saveDocumentAndApvAndVen(document, apvEmpIdList, selVendorId, empId,orderItems);

//        String s = savedDoc.getOrderItems().get(0).getProduct().getProductName();
//        System.out.println(s);
        redirectAttributes.addAttribute("status", true);


        return "redirect:/document/submitDoc/" + formNameEn + "/" + savedDoc.getDocId();
    }


//        String string = savedDoc.getDocDate().toString();
//      //자동으로 매핑이 안되어서 service에서 저장함.
//        //납품기한일
//        String string1 = savedDoc.getOrderDeadline().toString();
//
//        String s = "수신처 이름은?" +savedDoc.getVendor().getVendorName() +"기안일" +string +string1 ;
//
//        return s;
    //리턴되는 페이지 경로..!!!!


//
//    /**
//     * 문서 작성 상신 처리 - submit 처음 아님 (임시저장 이력 있는 경우, 수정하는 경우) - /{formNameEn}/{docId} 에서 진입
//     */
//    @PostMapping("/draft/{docId}")
//    public String submitDocument2(@PathVariable(name = "docId") Long docId,
//                                  Document document,
//                                  @RequestParam(name = "apvEmpId1") Long apvEmpId1,
//                                  @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2, //2차 결재자는 없을 수 있음 - 화면에서 유효성 처리
//                                  @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
//                                  RedirectAttributes redirectAttributes) {
//        String formNameEn = "draft";
//
//        List<Long> apvEmpIds = Arrays.asList(apvEmpId1, apvEmpId2);
//        documentService.updateDocumentAndApvAndRef(docId, document, apvEmpIds, refEmpIdList);
//
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/document/submitDoc/" + formNameEn + "/" + docId;
//    }
//

    /**
     * 문서 작성 임시 저장 처리 - /{formNameEn} 에서 진입
     */
    @PostMapping("/orderForm/temp")
    public String submitDocumentTemp(Principal p,
                                     @ModelAttribute(name = "document") Document document,
                                     @RequestParam(name = "apvEmpId1", required = false) Long apvEmpId1, //임시 저장은 값 없어도 됨
                                     @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2,
                                     @RequestParam(name = "selVendorId") int selVendorId,
                                     @RequestParam(name = "orderList") String orderList1,
                                     RedirectAttributes redirectAttributes) throws JsonProcessingException {

        String formNameEn = "orderForm";
        Long empId = Long.parseLong(p.getName()); //로그인 한 사원 번호



        //품목 저장 메서드
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode orderListNode = objectMapper.readTree(orderList1);
        List<OrderItem> orderItems = new ArrayList<>();

        //JsonNode에서 각 항목 추출
        for (JsonNode orderItemNode : orderListNode) {
            int productCode = orderItemNode.get("productCode").asInt();
            int quantity = orderItemNode.get("quantity").asInt();
            Product product = productService.getProduct((long) productCode).get();

            OrderItem orderItem = new OrderItem(product, quantity);
            orderItems.add(orderItem);
        }


        //Document 객체 정보 저장 : document, apvEmpIdList, refEmpIdList
        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
        Document savedDoc = documentService.saveDocumentAndApvAndVen(document, apvEmpIdList, selVendorId, empId,orderItems);


        Long docId = savedDoc.getDocId();

        redirectAttributes.addAttribute("tmpStatus", true);
        return "redirect:/document/createDoc/" + formNameEn + "/" + docId;



//        Document savedDoc = documentService.tempDocumentAndApvAndRef(document, apvEmpIdList, refEmpIdList, empId);
//
//        Long docId = savedDoc.getDocId();
//
//        redirectAttributes.addAttribute("tmpStatus", true);



    }
}
//
//    /**
//     * 문서 작성 임시 저장 처리 - 임시저장 submit 처음 아님 - /{formNameEn}/{docId} 에서 진입
//     */
//    @PostMapping("/temp2")
//    public String submitDocumentTemp2(Document document,
//                                      @RequestParam(name = "apvEmpId1", required = false) Long apvEmpId1, //임시 저장은 값 없어도 됨
//                                      @RequestParam(name = "apvEmpId2", required = false) Long apvEmpId2,
//                                      @RequestParam(name = "refEmpIdList", required = false) List<Long> refEmpIdList,
//                                      RedirectAttributes redirectAttributes) {
//
//        Long docId = document.getDocId();
//
//        List<Long> apvEmpIdList = Arrays.asList(apvEmpId1, apvEmpId2);
//        refEmpIdList = refEmpIdList != null ? refEmpIdList : Collections.emptyList();
//        Document savedDoc = documentService.temp2DocumentAndApvAndRef(docId, document, apvEmpIdList, refEmpIdList);
//
//        String formCode = savedDoc.getDocForm().getFormCode();
//
//        String formNameEn = docFormRepository.findByFormCode(formCode)
//                .map(DocForm::getFormNameEn)
//                .orElse(null);
//
//        redirectAttributes.addAttribute("tmpStatus2", true);
//        return "redirect:/document/createDoc/" + formNameEn + "/" + docId;
//    }
//}
//
