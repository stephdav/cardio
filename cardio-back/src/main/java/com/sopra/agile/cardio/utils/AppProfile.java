package com.sopra.agile.cardio.utils;

public enum AppProfile {
    cleardb, populatedb;

    public static boolean isAuthorized(String profile) {
        boolean isAuth = false;
        for (AppProfile p : AppProfile.values()) {
            if (p.name().equals(profile)) {
                isAuth = true;
                break;
            }
        }
        return isAuth;
    }
}
