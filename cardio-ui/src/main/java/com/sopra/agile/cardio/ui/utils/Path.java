package com.sopra.agile.cardio.ui.utils;

public class Path {
    public static class Web {
        public static final String BASE = "/ui/";

        public static final String ALL = BASE + "*";
        public static final String INDEX = BASE;
        public static final String ABOUT = BASE + "about/";
        public static final String PLANNING = BASE + "sprintPlanning/";
        public static final String SPRINT = BASE + "sprint/*";
        public static final String SPRINTS = BASE + "sprints/";
        public static final String USERS = BASE + "users/";
        public static final String VISION = BASE + "vision/";
    }

    public static class Template {
        public static final String INDEX = "templates/home.peb";
        public static final String ABOUT = "templates/about.peb";
        public static final String PLANNING = "templates/planning.peb";
        public static final String SPRINT = "templates/sprint.peb";
        public static final String SPRINTS = "templates/sprints.peb";
        public static final String USERS = "templates/users.peb";
        public static final String VISION = "templates/vision.peb";

        public static final String NOT_FOUND = "templates/404.peb";
    }
}