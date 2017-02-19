package com.sopra.agile.cardio.back.utils;

import java.util.List;

import com.sopra.agile.cardio.back.model.BootstrapTableList;

import spark.Response;

public class BootstrapTable<T> {

    public BootstrapTable() {
        // TODO Auto-generated constructor stub
    }

    public BootstrapTableList<T> convert(List<T> response, Response res) {
        BootstrapTableList<T> bta = new BootstrapTableList<T>();
        bta.setTotal(getTotalFromHeader(res));
        bta.setRows(response);
        return bta;
    }

    private int getTotalFromHeader(Response res) {
        int total = 0;
        String contentRange = res.raw().getHeader(Paginate.CONTENT_RANGE);
        if (contentRange != null) {
            String[] token = contentRange.split("/");
            if (token != null && token.length == 2) {
                total = Integer.parseInt(token[1]);
            }
        }
        return total;
    }
}
