package org.kosta.starducks.document.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.entity.Document;
import org.kosta.starducks.document.repository.CreateDocRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateDocService {
    private final CreateDocRepository createDocRepository;

    /**
     * id로 문서 정보 조회
     */
    public Optional<Document> getDocumentById(Long docId) {
        return createDocRepository.findById(docId);
    }


    //안쓸 것 같음
    /**
     * 업무 기안서 양식 작성 저장 - 첫 submit
     */
    public void firstSave(Document doc) {
        Document document = new Document();
        document.setDocWriter(doc.getDocWriter());
        document.setDocTitle(doc.getDocTitle());
        document.setDocContent(doc.getDocContent());
    }

    /**
     * 업무 기안서 양식 작성 저장 - 임시저장 이력 있는 경우
     */
    public void reSave(Document doc) {
        Document document = new Document();
        document.setDocWriter(doc.getDocWriter());
        document.setDocTitle(doc.getDocTitle());
        document.setDocContent(doc.getDocContent());
    }

    /**
     * 업무 기안서 양식 임시 저장 - 첫 submit
     */
    public void firstSaveTemp(Document doc) {
        Document document = new Document();
        document.setDocWriter(doc.getDocWriter());
        document.setDocTitle(doc.getDocTitle());
        document.setDocContent(doc.getDocContent());
    }

    /**
     * 업무 기안서 양식 임시 저장 완료 페이지 - 임시저장 이력 있는 경우
     */
    public void reSaveTemp(Document doc) {
        Document document = new Document();
        document.setDocWriter(doc.getDocWriter());
        document.setDocTitle(doc.getDocTitle());
        document.setDocContent(doc.getDocContent());
    }
}
