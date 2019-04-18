package com.seb.estoniaservice.controller;

import com.seb.translationcore.pojo.ErrorDetails;
import com.seb.translationcore.pojo.TranslationPojo;
import com.seb.estoniaservice.service.EstonianTranslation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class TranslationController implements ErrorController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SERVICE_HEADER_VALUE = "estonia";
    private static final String SERVICE_HEADER = "translation-service";
    private static final String API_PATH = "/translation/{word}";
    private static final String ERROR = "The path you've requested not found.";

    @RequestMapping(method = RequestMethod.GET, value = API_PATH)
    public TranslationPojo translateWord(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "word") String word) {
        logger.info(request.getMethod() + " request to " + request.getRequestURL());
        logger.info("Path variable name word is: " + word);
        response.addHeader(SERVICE_HEADER, SERVICE_HEADER_VALUE); // Adding this header in order to uppercase in pelican app.
        return EstonianTranslation.getTranslation();

    }

    @RequestMapping("/error")
    public ErrorDetails handleError() {
        logger.error("Unknown request");
        return new ErrorDetails(new Date(), ERROR, HttpStatus.NOT_FOUND.toString());
    }

    @Override
    public String getErrorPath() {
        return "/controller";
    }
}
