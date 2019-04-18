package com.seb.pelicanapp.controller;


import com.seb.translationcore.pojo.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * This controller is being used in purpose of requests to unknown paths.
 */

@RestController
public class CustomErrorController implements ErrorController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String ERROR = "The path you've requested not found.";

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
