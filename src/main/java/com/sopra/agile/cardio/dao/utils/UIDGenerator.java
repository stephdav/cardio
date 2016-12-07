package com.sopra.agile.cardio.dao.utils;

import java.util.UUID;

public class UIDGenerator {

	public static String getUniqueId(final String prefix) {
		return String.format("%s-%s", prefix, String.valueOf(UUID.randomUUID()));
	}
}
