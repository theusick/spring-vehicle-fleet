package com.theusick.frontend.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

@Component
public class CustomErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,
                                         HttpStatus status,
                                         Map<String, Object> model) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains(MediaType.TEXT_HTML_VALUE)) {
            ModelAndView mav = new ModelAndView("pages/error/error", model);
            appendErrorAttrsToModel(request, mav);
            return mav;
        }
        return null;
    }

    private void appendErrorAttrsToModel(HttpServletRequest request,
                                         ModelAndView model) {
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
        model.addObject("errorMessage", message.toString());
    }

}
