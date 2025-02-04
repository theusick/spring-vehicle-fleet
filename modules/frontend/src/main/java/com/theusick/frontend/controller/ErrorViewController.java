package com.theusick.frontend.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
public class ErrorViewController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,
                              Model model) {
        Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        StringBuilder message = new StringBuilder();
        if (Objects.nonNull(errorStatusCode)) {
            message.append("Error code: ").append(errorStatusCode).append(". ");
        }
        if (Objects.nonNull(errorMessage)) {
            message.append(errorMessage);
        } else {
            message.append("Unexpected error occurred.");
        }
        model.addAttribute("errorMessage", message.toString());
        return "pages/error/error";
    }

}
