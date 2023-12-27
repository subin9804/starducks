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

    public EmpFile getEmpFile(Long empId, String type) {

        return fileRepository.findByEmpIdAndType(empId, type);
    }


    // 파일 경로 설정
    public void addFileInfo(EmpFile empFile) {

        String originFileName = empFile.getFileName();
        String type = empFile.getType();
        Long empId = empFile.getEmpId();
        String extension = originFileName.contains(".") ?
                originFileName.substring(originFileName.indexOf(".")) :
                "";

        String newFileName = null;
        String fileDir = null;  // 파일 서버 업로드 폴더
        String _fileUrl = null;

        if (type.equals("profile")) {
            newFileName = "profile_" + empId + extension;
            fileDir = uploadPath + "profileImg";
            _fileUrl = uploadUrl + "profileImg";

        } else if (type.equals("stamp")) {
            newFileName = "stamp_" + empId + extension;
            fileDir = uploadPath + "stampImg";
            _fileUrl = uploadUrl + "stampImg";
        } else {
            fileDir = uploadPath;
            _fileUrl = uploadUrl;
        }

        File _fileDir = new File(fileDir);
        if (!_fileDir.exists()) {
            _fileDir.mkdir();
        }

        String filePath = fileDir + "/" + newFileName;
        String fileUrl = _fileUrl + "/" + newFileName;

        empFile.setFilePath(filePath);
        empFile.setFileUrl(fileUrl);
    }

    /**
     * 파일 업로드 (생성 / 수정)
     * @param file  업로드할 파일
     * @param type  파일이 도장(stamp)인지 프로필사진(profile)인지
     * @param empId 파일 주인 아이디
     * @return
     */
    public EmpFile upload(MultipartFile file, String type, Long empId) {

        // 기존 파일 정보 - 존재하면 수정, 없으면 생성
        Boolean exist = fileRepository.existsByEmpIdAndType(empId, type);
        EmpFile empFile = null;
        String originFileName = file.getOriginalFilename();

        // 생성
        if (!originFileName.equals("") && !originFileName.isBlank() && !originFileName.isEmpty()) {
            if (!exist) {

                // DB에 저장
                empFile = new EmpFile();
                empFile.setFileName(originFileName);
                empFile.setEmpId(empId);
                empFile.setType(type);

                // 파일 경로 설정
                addFileInfo(empFile);
            } else {

                // 수정
                empFile = fileRepository.findByEmpIdAndType(empId, type);

                // DB에 저장
                empFile.setFileName(originFileName);

            }

            try {
                File _file = new File(empFile.getFilePath());
                file.transferTo(_file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            fileRepository.saveAndFlush(empFile);

        }

        return empFile;
    }

    /**
     * Template에 넘길 이미지 url 가져오기
     * @param empId
     * @param type
     * @return
     */
    public String getFileUrl(Long empId, String type) {
        String url = null;

        // 파일
        EmpFile file = fileRepository.findByEmpIdAndType(empId, type);

        if (file != null) {
            url = file.getFileUrl();
        } else {
            // 현기님 커스텀
            if(empId == 11) {
                if(type.equals("profile")) {
                    url = "/images/cat1.jpg";
                } else {
                    url = "/images/stamp/도장이미지_이기현인.png";
                }
            } else {
                if(type.equals("profile")) {
                    url = "/images/noProfile.png";
                } else {
                    url = "/images/noStamp.png";
                }
            }
        }

        return url;
    }

}
