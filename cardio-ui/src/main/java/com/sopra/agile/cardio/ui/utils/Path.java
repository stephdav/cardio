package com.sopra.agile.cardio.ui.utils;

public class Path {
    public static class Web {
        public static final String BASE = "/ui/";
        public static final String ALL = BASE + "*";
        public static final String INDEX = BASE;
        public static final String ADMIN = BASE + "admin/";
        public static final String USERS = ADMIN + "users/";
        public static final String SPRINTS = ADMIN + "sprints/";
        public static final String SPRINT = BASE + "sprint/*";
    }

    public static class Template {
        public static final String INDEX = "templates/home.peb";
        public static final String ADMIN = "templates/admin.peb";
        public static final String USERS = "templates/users.peb";
        public static final String SPRINTS = "templates/sprints.peb";
        public static final String SPRINT = "templates/sprint.peb";
        public static final String NOT_FOUND = "templates/404.peb";
    }
}