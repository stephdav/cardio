package com.sopra.agile.cardio.ui.utils;

public class Path {
    public static class Web {
        public static final String BASE = "/ui/";
        public static final String ALL = BASE + "*";
        public static final String INDEX = BASE;
        public static final String ADMIN = BASE + "admin/";
        public static final String USERS = BASE + "users/";
    }

    public static class Template {
        public static final String INDEX = "templates/home.peb";
        public static final String ADMIN = "templates/admin.peb";
        public static final String USERS = "templates/users.peb";
        public static final String NOT_FOUND = "templates/404.peb";
    }
}