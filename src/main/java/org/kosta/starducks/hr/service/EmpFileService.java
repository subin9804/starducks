package org.kosta.starducks.hr.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.entity.EmpFile;
import org.kosta.starducks.hr.repository.EmpFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmpFileService {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.url}")
    private String uploadUrl;

    private final EmpFileRepository fileRepository;


    // 파일 정보 재구성
    public void addFileInfo(EmpFile empFile) {

        String originFileName = empFile.getFileName();
        String type = empFile.getType();
        Long empId = empFile.getEmpId();
        String extension = originFileName.substring(originFileName.indexOf("."));

        String newFileName = null;
        String fileDir = null;  // 파일 서버 업로드 폴더

        if(type.equals("profile")) {
            newFileName = "profile_" + empId + extension;
            fileDir = uploadPath + "/" + "profileImg";

        } else if (type.equals("stamp")) {
            newFileName = "stamp_" + empId + extension;
            fileDir = uploadPath + "/" + "stampImg";
        } else {
            fileDir = uploadPath;
        }

        File _fileDir = new File(fileDir);
        if(!_fileDir.exists()) {
            _fileDir.mkdir();
        }

        String filePath = fileDir + "/" + newFileName;
        String fileUrl = uploadUrl + newFileName;

        empFile.setFilePath(filePath);
        empFile.setFileUrl(fileUrl);
    }

    // 파일 업로드
    public EmpFile upload(MultipartFile file, String type, Long empId) {
        String originFileName = file.getOriginalFilename();

        // DB에 저장
        EmpFile empFile = new EmpFile();
        empFile.setFileName(originFileName);
        empFile.setEmpId(empId);
        empFile.setType(type);
//        empFile.setFilePath(filePath);
//        empFile.setFileUrl(fileUrl);

        addFileInfo(empFile);

        try {
            File _file = new File(empFile.getFilePath());
            file.transferTo(_file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fileRepository.saveAndFlush(empFile);

        return empFile;
    }

}
