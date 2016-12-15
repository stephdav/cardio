package com.sopra.agile.cardio.back.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;

public class Paginate<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Paginate.class);

    private static final String ACCEPT_RANGE = "Accept-Range";
    private static final String CONTENT_RANGE = "Content-Range";

    private static final String PAGE = "page";
    private static final String LIMIT = "limit";

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_LIMIT = 20;

    private final Class<T> clazz;

    public Paginate(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> paginate(Request req, Response res, List<T> response) {

        // === Input parameters ===

        int page = DEFAULT_PAGE;
        if (req.queryParams(PAGE) != null) {
            page = Integer.parseInt(req.queryParams(PAGE));
        }

        int limit = DEFAULT_LIMIT;
        if (req.queryParams(LIMIT) != null) {
            limit = Integer.parseInt(req.queryParams(LIMIT));
        }

        int total = response.size();

        // === Status code ===

        int httpCode = 206;
        if (limit > DEFAULT_LIMIT) {
            limit = DEFAULT_LIMIT;
            res.status(400);
        } else if (total < limit) {
            httpCode = 200;
        }
        LOGGER.debug("HTTP-Code: {}", httpCode);
        res.status(httpCode);

        // === Compute indexes ===

        int fromIndex = (page - 1) * limit;
        int toIndex = page * limit;

        if (toIndex > total) {
            toIndex = total;
        }

        // === Response header ===

        String acceptRange = String.format("%s %d", clazz.getSimpleName().toLowerCase(), DEFAULT_LIMIT);
        LOGGER.debug("{}: {}", ACCEPT_RANGE, acceptRange);
        res.header(ACCEPT_RANGE, acceptRange);

        String contentRange = String.format("%d-%d/%d", fromIndex, toIndex, total);
        LOGGER.debug("{}: {}", CONTENT_RANGE, contentRange);
        res.header(CONTENT_RANGE, contentRange);

        return response.subList(fromIndex, toIndex);
    }

    public List<T> paginateFromTo(Request req, List<T> response) {
        int fromIdx = 0;
        int toIdx = 25;
        if (req.queryParams("range") != null) {
            String range = req.queryParams("range");
            LOGGER.debug("range=" + range);
            String[] bounds = range.split("-");
            fromIdx = Integer.parseInt(bounds[0]);
            toIdx = Integer.parseInt(bounds[1]);

            if (fromIdx > toIdx) {
                int tmp = toIdx;
                toIdx = fromIdx;
                fromIdx = tmp;
            }
            if (fromIdx < 0) {
                fromIdx = 0;
            }
        }

        if (fromIdx >= response.size() - 1) {
            fromIdx = response.size() - 1;
        }
        if (toIdx > response.size()) {
            toIdx = response.size();
        }
        return response.subList(fromIdx, toIdx);
    }
}
