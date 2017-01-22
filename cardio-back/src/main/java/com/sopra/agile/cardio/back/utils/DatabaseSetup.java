package com.sopra.agile.cardio.back.utils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSetup {

    private static final List<String> scripts = new ArrayList<String>();

    private DatabaseSetup() {
        // static class
    }

    public static List<String> getScripts() {
        return scripts;
    }

    public static void addScript(String path) {
        scripts.add(path);
    }
}
