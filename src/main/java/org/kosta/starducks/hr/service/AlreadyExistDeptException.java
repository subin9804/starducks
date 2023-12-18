package org.kosta.starducks.hr.service;

import org.kosta.starducks.commons.CommonException;
import org.springframework.http.HttpStatus;

public class AlreadyExistDeptException extends CommonException {
    public AlreadyExistDeptException(String message) {
        super(bundleError.getString(message), HttpStatus.BAD_REQUEST);
    }

}
