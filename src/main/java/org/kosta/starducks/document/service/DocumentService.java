package org.kosta.starducks.document.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.entity.*;
import org.kosta.starducks.document.repository.ApprovalRepository;
import org.kosta.starducks.document.repository.DocumentRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final ApprovalRepository approvalRepository;
    private final EmpRepository empRepository;

    /**
     * ApvEmp(결재자)의 EmpId로 document 리스트 가져오기
     */
    public List<Document> findDocumentsByApprovalEmpId(Long empId) {
        List<Approval> approvals = approvalRepository.findByApvEmp_EmpId(empId);
        List<Document> documents = approvals.stream()
                .map(Approval::getDocument)
                .collect(Collectors.toList());

        return documents;
    }

    /**
     * document와 자식 객체인 Approval, RefEmployee 객체 저장
     */
    public Document saveDocumentAndApvAndRef(Document document, List<Long> apvEmpIds, List<Long> refEmpIds) {
        List<Approval> approvalList = new ArrayList<>();
        int i = 1;
        for (Long apvEmpId : apvEmpIds) {
            Approval approval = new Approval();
            approval.setApvStep(i++);
            approval.setApvStatus(ApvStatus.PENDING);
            approval.setApvEmp(empRepository.findById(apvEmpId)
                    .orElseThrow(() -> new EntityNotFoundException("찾는 apvEmpId와 일치하는 사원 엔티티 없음 : " + apvEmpId)));
            approval.setDocument(document);

            approvalList.add(approval);
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapprovalㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+approval);
        }

        List<RefEmployee> refEmpList = new ArrayList<>();
        if (refEmpIds != null) {
            for (Long refEmpId : refEmpIds) {
                RefEmployee refEmployee = new RefEmployee();
                refEmployee.setEmployee(empRepository.findById(refEmpId)
                        .orElseThrow(() -> new EntityNotFoundException("찾는 refEmpId와 일치하는 사원 엔티티 없음 : " + refEmpId)));
                refEmployee.setDocument(document);

                refEmpList.add(refEmployee);
                System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡrefEmployeeㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+refEmployee);
            }
        }

        Long empId = 1L; //로그인한 사원 번호
        document.setDocWriter(empRepository.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("찾는 로그인 한 empId와 일치하는 사원 엔티티 없음 : " + empId)));
        document.setDocDate(LocalDateTime.now());
        document.setDocStatus(DocStatus.PENDING_DOC);
        document.setApproval(approvalList);
        document.setRefEmployee(refEmpList);
//        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡdocumentㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+document);
        Document savedDoc = documentRepository.save(document);

        return savedDoc;
    }



    /**
     * document와 자식 객체인 Approval, RefEmployee 객체 수정
     */
    public Document updateDocumentAndApvAndRef(Long docId, Document document, List<Long> apvEmpIds, List<Long> refEmpIds) {
        Document existingDocument = documentRepository.findById(docId)
                .orElseThrow(() -> new EntityNotFoundException("찾는 docId와 일치하는 문서 엔티티 없음 : " + docId));

        List<Approval> existingApprovals = approvalRepository.findByDocument_DocId(docId); //기존 docId로 저장됐던 Approval 리스트
        int apvStep = 1;
        for (Long apvEmpId : apvEmpIds) {
            //기존 docId & apvStep으로 저장됐던 Approval의 ApvEmp 다시 set
            existingApprovals.get(apvStep).setApvEmp(empRepository.findById(apvEmpId)
                    .orElseThrow(() -> new EntityNotFoundException("찾는 apvEmpId와 일치하는 사원 엔티티 없음 : " + apvEmpId)));
            apvStep++;
        }

        List<RefEmployee> refEmpList = new ArrayList<>(); //수정 후에도 참조된 사원이 계속 참조될 수도, 새로 참조될 수도, 참조 안될수도 - 객체 새로 넣기
        if (refEmpIds != null) {
            for (Long refEmpId : refEmpIds) {
                RefEmployee refEmployee = new RefEmployee();
                refEmployee.setEmployee(empRepository.findById(refEmpId)
                        .orElseThrow(() -> new EntityNotFoundException("찾는 refEmpId와 일치하는 사원 엔티티 없음 : " + refEmpId)));
                refEmployee.setDocument(document);

                refEmpList.add(refEmployee);
            }
        }

            Long empId = 1L; // 로그인한 사원 번호
            document.setDocWriter(empRepository.findById(empId)
                    .orElseThrow(() -> new EntityNotFoundException("찾는 로그인 한 empId와 일치하는 사원 엔티티 없음 : " + empId)));
            if (document.getDocStatus() == DocStatus.TEMP_STORED) {
                document.setDocDate(LocalDateTime.now());
            } else if (document.getDocStatus() == DocStatus.PENDING_DOC && document.getDocUpdateDate() == null) {
                document.setDocUpdateDate(LocalDateTime.now());
            }

//            document.setApproval(approvalList);
            document.setRefEmployee(refEmpList);
            document.setDocStatus(DocStatus.PENDING_DOC);

            Document savedDoc = documentRepository.save(document);

            return savedDoc;
        }




    /**
     * document와 자식 객체인 Approval, RefEmployee 객체 임시 저장
     */
    public Document tempDocumentAndApvAndRef(Document document, List<Long> apvEmpIds, List<Long> refEmpIds) {
        List<Approval> approvalList = new ArrayList<>();
        int i = 1;
        for (Long apvEmpId : apvEmpIds) {
            Approval approval = new Approval();
            approval.setApvStep(i++);
            approval.setApvStatus(ApvStatus.PENDING);
            approval.setApvEmp(empRepository.findById(apvEmpId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + apvEmpId)));
            approval.setDocument(document);

            approvalList.add(approval);

        }

        List<RefEmployee> refEmpList = new ArrayList<>();
        if (refEmpIds != null) {
            for (Long refEmpId : refEmpIds) {
                RefEmployee refEmployee = new RefEmployee();
                refEmployee.setEmployee(empRepository.getById(refEmpId));
                refEmployee.setDocument(document);

                refEmpList.add(refEmployee);
            }
        }

        Long empId = 1L; //로그인한 사원 번호
        document.setDocWriter(empRepository.getById(empId));
        document.setDocDate(LocalDateTime.now());
        document.setDocStatus(DocStatus.TEMP_STORED);
        document.setApproval(approvalList);
        document.setRefEmployee(refEmpList);
        Document savedDoc = documentRepository.save(document);

        return savedDoc;
    }

    /**
     * docId로 ApvEmpId 리스트 가져오기
     */
    public List<Long> getApvEmpIdsByDocId(Long docId) {
        // 문서 ID를 기반으로 문서를 가져옴
        Document document = documentRepository.findById(docId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + docId));

        // 문서에 연결된 Approval 엔터티들을 가져옴
        List<Approval> approvals = document.getApproval();

        // Approval 엔터티에서 apvEmpId 추출
        List<Long> apvEmpIds = approvals.stream()
                .map(approval -> approval.getApvEmp().getEmpId())
                .collect(Collectors.toList());

        return apvEmpIds;
    }

    /**
     * docId로 RefEmpId 리스트 가져오기
     */
    public List<Long> getRefEmpIdsByDocId(Long docId) {
        // 문서 ID를 기반으로 문서를 가져옴
        Document document = documentRepository.findById(docId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + docId));

        // 문서에 연결된 RefEmployee 엔터티들을 가져옴
        List<RefEmployee> refEmployees = document.getRefEmployee();

        // RefEmployee 엔터티에서 refEmpId 추출
        List<Long> refEmpIds = refEmployees.stream()
                .map(refEmployee -> refEmployee.getEmployee().getEmpId())
                .collect(Collectors.toList());

        return refEmpIds;
    }
}
