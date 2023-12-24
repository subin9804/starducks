package org.kosta.starducks.document.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.entity.*;
import org.kosta.starducks.document.repository.ApprovalRepository;
import org.kosta.starducks.document.repository.DocumentRepository;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final ApprovalRepository approvalRepository;
    private final EmpRepository empRepository;

    /**
     * docWriter(기안자, 문서 작성자)의 EmpId로 document 리스트 가져오기 - 결재 상신함 페이징 처리
     */
    public Page<Document> submitDocuments(Long empId, Pageable pageable) {
        //로그인한 사원이 작성한 문서 데이터
        List<Document> documents = new ArrayList<>();
        for (Document document : documentRepository.findByDocWriter_EmpId(empId)) {
            if (document.getDocStatus() != DocStatus.TEMP_STORED) { //임시저장 문서 제외
                documents.add(document);
            }
        }

        //페이징 처리하여 리턴
        return new PageImpl<>(documents, pageable, documents.size());
    }

    /**
     * docWriter(기안자, 문서 작성자)의 EmpId로 document 리스트 가져오기 - 결재 상신함 페이징 처리, 검색 처리
     */
    public Page<Document> searchSubmitDocuments(Long empId, String keyword, Pageable pageable) {
        //로그인한 사원이 작성한 문서 데이터
        List<Document> documents = documentRepository.findByDocWriter_EmpId(empId);

        //페이지 검색 기능
        List<Document> documentsBySearch = documentRepository.findByDocTitleContainingOrDocContentContaining(keyword, keyword);

        //결재 수신 문서와 검색 결과 리스트의 교집합 구하기
        List<Document> intersection = documentsBySearch.stream()
                .filter(document -> document.getDocStatus() != DocStatus.TEMP_STORED) //임시저장 문서 제외
                .filter(documents::contains)
                .toList();


        //페이징 처리하여 리턴 (데이터 많을 때 intersection.subList(start, end)하여 리턴 시 메모리 사용량 줄이기 가능)
        return new PageImpl<>(intersection, pageable, documents.size());
    }

    /**
     * 전자 결재 홈 결재 상신함 박스
     */
    public List<Document> homeSubmitDocuments(Long empId) {
        //로그인한 사원이 작성한 문서 데이터
        List<Document> documents = new ArrayList<>();
        for (Document document : documentRepository.findByDocWriter_EmpId(empId)) {
            if (document.getDocStatus() != DocStatus.TEMP_STORED) { //임시저장 문서 제외
                documents.add(document);
            }
        }
        // 최근 10개만 추출
        documents.sort(Comparator.comparing(Document::getDocId).reversed());
        List<Document> recentSubmitDocs = documents.stream()
                .limit(10)
                .collect(Collectors.toList());

        return recentSubmitDocs;
    }


    /**
     * ApvEmp(결재자)의 EmpId로 document 리스트 가져오기 - 결재 수신함 페이징 처리
     */
    public Page<Document> receiveDocuments(Long empId, Pageable pageable) {
        //로그인한 사원이 결재자인 결재 데이터
        List<Approval> approvals = approvalRepository.findByApvEmp_EmpId(empId);

        //그 결재 데이터를 포함한 문서 데이터 (로그인 한 사원의 결재 수신함 데이터)
        List<Document> documents = new ArrayList<>();
        for (Approval approval : approvals) {
            Document document = approval.getDocument();
            //임시저장 문서 제외
            if (document.getDocStatus() != DocStatus.TEMP_STORED) {
                documents.add(document);

            }
        }

        //페이징 처리하여 리턴
        return new PageImpl<>(documents, pageable, documents.size());
    }

    /**
     * ApvEmp(결재자)의 EmpId로 document 리스트 가져오기 - 결재 수신함 페이징 처리, 검색 처리
     */
    public Page<Document> searchReceiveDocuments(Long empId, String keyword, Pageable pageable) {
        //로그인한 사원이 결재자인 결재 데이터
        List<Approval> approvals = approvalRepository.findByApvEmp_EmpId(empId);

        //그 결재 데이터를 포함한 문서 데이터 (로그인 한 사원의 결재 수신함 데이터)
        List<Document> documents = new ArrayList<>();
        for (Approval approval : approvals) {
            Document document = approval.getDocument();
            //임시저장 문서 제외
            if (document.getDocStatus() != DocStatus.TEMP_STORED) {
                documents.add(document);

            }
        }

        //페이지 검색 기능
        List<Document> documentsBySearch = documentRepository.findByDocTitleContainingOrDocContentContaining(keyword, keyword);

        //결재 수신 문서와 검색 결과 리스트의 교집합 구하기
        List<Document> intersection = documentsBySearch.stream()
                .filter(documents::contains)
                .toList();

        //페이징 처리하여 리턴 (데이터 많을 때 intersection.subList(start, end)하여 리턴 시 메모리 사용량 줄이기 가능)
        return new PageImpl<>(intersection, pageable, documents.size());
    }

    /**
     * 전자 결재 홈 결재 수신함 박스
     */
    public List<Document> homeReceiveDocuments(Long empId) {
        //로그인한 사원이 결재자인 결재 데이터
        List<Approval> approvals = approvalRepository.findByApvEmp_EmpId(empId);

        //그 결재 데이터를 포함한 문서 데이터 (로그인 한 사원의 결재 수신함 데이터)
        List<Document> documents = new ArrayList<>();
        for (Approval approval : approvals) {
            Document document = approval.getDocument();
            //임시저장 문서 제외
            if (document.getDocStatus() != DocStatus.TEMP_STORED) {
                documents.add(document);
            }
        }

        // 최근 10개만 추출
        documents.sort(Comparator.comparing(Document::getDocId).reversed());
        List<Document> recentReceiveDocs = documents.stream()
                .limit(4)
                .collect(Collectors.toList());

        return recentReceiveDocs;
    }

    /**
     * docWriter(기안자, 문서 작성자)의 EmpId로 document 리스트 가져오기 - 임시저장함 페이징 처리
     */
    public Page<Document> tempDocuments(Long empId, Pageable pageable) {
        //로그인한 사원이 작성한 문서 데이터
        List<Document> documents = new ArrayList<>();
        for (Document document : documentRepository.findByDocWriter_EmpId(empId)) {
            if (document.getDocStatus() == DocStatus.TEMP_STORED) { //임시저장 문서만
                documents.add(document);
            }
        }

        //페이징 처리하여 리턴
        return new PageImpl<>(documents, pageable, documents.size());
    }

    /**
     * docWriter(기안자, 문서 작성자)의 EmpId로 document 리스트 가져오기 - 임시저장함 페이징 처리, 검색 처리
     */
    public Page<Document> searchTempDocuments(Long empId, String keyword, Pageable pageable) {
        //로그인한 사원이 작성한 문서 데이터
        List<Document> documents = documentRepository.findByDocWriter_EmpId(empId);

        //페이지 검색 기능
        List<Document> documentsBySearch = documentRepository.findByDocTitleContainingOrDocContentContaining(keyword, keyword);

        //결재 수신 문서와 검색 결과 리스트의 교집합 구하기
        List<Document> intersection = documentsBySearch.stream()
                .filter(document -> document.getDocStatus() == DocStatus.TEMP_STORED) //임시저장 문서만
                .filter(documents::contains)
                .toList();


        //페이징 처리하여 리턴 (데이터 많을 때 intersection.subList(start, end)하여 리턴 시 메모리 사용량 줄이기 가능)
        return new PageImpl<>(intersection, pageable, documents.size());
    }

    /**
     * 전자 결재 홈 임시 저장함 박스
     */
    public List<Document> homeTempDocuments(Long empId) {
        //로그인한 사원이 작성한 문서 데이터
        List<Document> documents = new ArrayList<>();
        for (Document document : documentRepository.findByDocWriter_EmpId(empId)) {
            if (document.getDocStatus() == DocStatus.TEMP_STORED) { //임시저장 문서만
                documents.add(document);
            }
        }

        // 최근 10개만 추출
        documents.sort(Comparator.comparing(Document::getDocId).reversed());
        List<Document> recentTempDocs = documents.stream()
                .limit(10)
                .collect(Collectors.toList());

        return recentTempDocs;
    }

//    //페이징 처리
//    public Page<Document> docList(Pageable pageable) {
//        return documentRepository.findAll(pageable);
//    }
//
//    //페이지 검색 기능 구현
//    public Page<Document> docSearchList(String keyword, Pageable pageable) {
//        return documentRepository.findByDocTitleContainingOrDocContentContaining(keyword, keyword, pageable);
//    }

    /**
     * document와 자식 객체인 Approval, RefEmployee 객체 저장 - 첫 submit
     */
    public Document saveDocumentAndApvAndRef(Document document, List<Long> apvEmpIdList, List<Long> refEmpIdList, Long empId) {
        //Document에 저장할 Approval을 저장
        List<Approval> approvalList = new ArrayList<>();
        int i = 1;
        for (Long apvEmpId : apvEmpIdList) {
            Approval approval = new Approval();
            approval.setApvStep(i++);
            approval.setApvStatus(ApvStatus.PENDING);
            empRepository.findById(apvEmpId)
                    .ifPresent(approval::setApvEmp);
            approval.setDocument(document); //안해줘도 연결되나...?

            approvalList.add(approval);
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapprovalㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+approval);
        }

        //폼에서 저장한 urgent, docTitle, docContent 제외하고 set
        empRepository.findById(empId)
                .ifPresent(document::setDocWriter);
        document.setDocDate(LocalDateTime.now());
        document.setDocStatus(DocStatus.PENDING_DOC);
        document.setApprovals(approvalList);
        document.setRefEmpIds(refEmpIdList.toString());
//        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡdocumentㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+document);
        Document savedDoc = documentRepository.save(document);

        return savedDoc;
    }

    /**
     * document와 자식 객체인 Approval, RefEmployee 객체 수정 - submit 처음 아님 (임시저장 이력 있는 경우, 수정하는 경우)
     */
    public Document updateDocumentAndApvAndRef(Long docId, Document document, List<Long> apvEmpIdList, List<Long> refEmpIdList, Long empId) {
        //수정이므로 저장한 기존 Document 객체에 추가 저장
        Document existingDocument = documentRepository.findById(docId)
                .orElseThrow(() -> new EntityNotFoundException("찾는 docId와 일치하는 문서 엔티티 없음 : " + docId));

        //기존 docId로 저장한 기존 Approval 리스트의 apvEmpId를 다시 set
        List<Approval> existingApvList = approvalRepository.findByDocument_DocId(docId);
        int apvStep = 1;
        for (Long apvEmpId : apvEmpIdList) {
            //기존 docId & apvStep으로 저장됐던 Approval의 ApvEmp 다시 set
            empRepository.findById(apvEmpId)
                    .ifPresent(existingApvList.get(apvStep - 1)::setApvEmp);
            apvStep++;
        }

        //임시저장, 수정 : 상태, 작성일 다르게 set
        if (existingDocument.getDocStatus() == DocStatus.PENDING_DOC) {
            //수정 - 수정일 set
            existingDocument.setDocUpdateDate(LocalDateTime.now());
        } else if (existingDocument.getDocStatus() == DocStatus.TEMP_STORED) {
            //임시저장 - 상신(대기) 상태로 set, 작성일 다시 set
            existingDocument.setDocDate(LocalDateTime.now());
        }

        //폼으로 저장한 데이터 set
        existingDocument.setUrgent(document.isUrgent());
        existingDocument.setDocTitle(document.getDocTitle());
        existingDocument.setDocContent(document.getDocContent());

        //폼에서 저장한 데이터, 위에서 이미 set한 데이터 제외하고 set
        empRepository.findById(empId)
                .ifPresent(existingDocument::setDocWriter);
        existingDocument.setDocStatus(DocStatus.PENDING_DOC);
        existingDocument.setApprovals(existingApvList);
        existingDocument.setRefEmpIds(refEmpIdList.toString());

        Document savedDoc = documentRepository.save(existingDocument);

        return savedDoc;
    }

    /**
     * document와 자식 객체인 Approval, RefEmployee 객체 임시 저장 - 첫 임시저장 submit
     */
    public Document tempDocumentAndApvAndRef(Document document, List<Long> apvEmpIdList, List<Long> refEmpIdList, Long empId) {
        //Document에 저장할 Approval을 저장
        List<Approval> approvalList = new ArrayList<>();
        int i = 1;
        for (Long apvEmpId : apvEmpIdList) {
            if (apvEmpId != null) { // 결재자 선택하지 않고 임시저장 한 경우
                Approval approval = new Approval();
                approval.setApvStep(i++);
                approval.setApvStatus(ApvStatus.PENDING);
                empRepository.findById(apvEmpId)
                        .ifPresent(approval::setApvEmp);
                approval.setDocument(document); //안해줘도 연결되나...?

                approvalList.add(approval);
            } else {
                Approval approval = null;

                approvalList.add(approval);
            }
        }

        //폼에서 저장한 urgent, docTitle, docContent 제외하고 set
        empRepository.findById(empId)
                .ifPresent(document::setDocWriter);
        document.setDocDate(LocalDateTime.now());
        document.setDocStatus(DocStatus.TEMP_STORED);
        document.setApprovals(approvalList);
        if (refEmpIdList != null) { // 참조자 선택하지 않고 임시저장 한 경우
            document.setRefEmpIds(refEmpIdList.toString());
        }
        Document savedDoc = documentRepository.save(document);

        return savedDoc;
    }

    /**
     * document와 자식 객체인 Approval, RefEmployee 객체 임시 저장 - submit 처음 아님 (임시저장 이력 있는 경우)
     */
    public Document temp2DocumentAndApvAndRef(Long docId, Document document, List<Long> apvEmpIdList, List<Long> refEmpIdList, Long empId) {
        //수정이므로 저장한 기존 Document 객체에 추가 저장
        Document existingDocument = documentRepository.findById(docId)
                .orElseThrow(() -> new EntityNotFoundException("찾는 docId와 일치하는 문서 엔티티 없음 : " + docId));

        //기존 docId로 저장한 기존 Approval 리스트의 apvEmpId를 다시 set
        List<Approval> existingApvList = approvalRepository.findByDocument_DocId(docId); //리스트 두개 가져오는거 맞는지 확인
        int apvStep = 1;
        for (Long apvEmpId : apvEmpIdList) {
            //기존 docId & apvStep으로 저장됐던 Approval의 ApvEmp 다시 set
            empRepository.findById(apvEmpId)
                    .ifPresent(existingApvList.get(apvStep - 1)::setApvEmp);
            apvStep++;
        }

        //폼으로 저장한 데이터 set
        existingDocument.setUrgent(document.isUrgent());
        existingDocument.setDocTitle(document.getDocTitle());
        existingDocument.setDocContent(document.getDocContent());

        //폼에서 저장한 데이터, 위에서 이미 set한 데이터 제외하고 set
        empRepository.findById(empId)
                .ifPresent(existingDocument::setDocWriter);
        existingDocument.setDocDate(LocalDateTime.now());
        existingDocument.setApprovals(existingApvList);
        existingDocument.setRefEmpIds(refEmpIdList.toString());

        Document savedDoc = documentRepository.save(existingDocument);

        return savedDoc;
    }

    /**
     * docId로 ApvEmpId 리스트 가져오기
     */
    public List<Long> getApvEmpIdsByDocId(Long docId) {
        // 문서 ID를 기반으로 문서를 가져옴
        Document document = documentRepository.findById(docId)
                .orElse(null);

        // 문서에 연결된 Approval 엔터티들을 가져옴
        List<Approval> approvals = document.getApprovals();

        // Approval 엔터티에서 apvEmpId 리스트 추출
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

        // 문서에 저장된 refEmpIds 리스트(String)을 가져옴
        String refEmpIds = document.getRefEmpIds();

        // 대괄호 "["와 "]"를 제거한 후에 리스트로 parsing
        List<Long> refEmpIdList = Optional.ofNullable(refEmpIds)
                .map(ids -> Arrays.stream(ids.replace("[", "").replace("]", "").split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty()) //빈 객체 파싱 방지
                        .map(Long::parseLong)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        return refEmpIdList;
    }
}
