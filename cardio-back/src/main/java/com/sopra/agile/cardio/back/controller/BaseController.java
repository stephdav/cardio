package com.sopra.agile.cardio.back.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Response;

public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    protected static final String LOCATION = "Location";

    public BaseController() {
        // Empty constructor
    }

    protected HttpServletResponse export(Response res, String filename, String content) {

        byte[] data = content.getBytes();

        LOGGER.info("export {} bytes in file {}", data.length, filename);

        HttpServletResponse raw = res.raw();
        res.header("Content-Disposition", "attachment; filename=" + filename);
        res.type("application/force-download");
        try {
            raw.getOutputStream().write(data);
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
        } catch (IOException ioe) {
            LOGGER.info("Error when exporting to file {} : {}", filename, ioe.getMessage());
        }
        return raw;
    }
}
