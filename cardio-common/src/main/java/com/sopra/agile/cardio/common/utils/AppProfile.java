package com.sopra.agile.cardio.common.utils;

public enum AppProfile {

    cleardb, populatedb;

    /**
     * Check if a profile is known.
     * 
     * @param profile
     * @return <code>true</code> if profile is authorized, <code>false</code>
     *         otherwise.
     */
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
