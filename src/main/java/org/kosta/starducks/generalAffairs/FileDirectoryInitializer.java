package org.kosta.starducks.generalAffairs;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FileDirectoryInitializer {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @PostConstruct
    public void initialize(){

        //File 객체 생성
        File directory = new File(fileUploadPath);

        //해당 경로 없다면 폴더 생성하기
        if(!directory.exists()) {
            boolean success = directory.mkdirs();
            if (success) {
                log.info("폴더가 생성되었습니다.");
            } else {
                log.info("폴더 생성을 실패하였습니다.");
            }
        }
        else {
            log.info("폴더가 이미 존재합니다.");
        }

            }
        }


