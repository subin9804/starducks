package org.kosta.starducks.commons;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @Autowired
    private HttpServletResponse response;

    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model) {
        e.printStackTrace();

        String msg = e.getMessage();
        HttpStatus status = e.getStatus();
        response.setStatus(status.value());

        String script = String.format("errorAlert(%s).then(() => {history.back()});", msg);

        model.addAttribute("script", script);

        return "commons/execute_script";
    }
}
