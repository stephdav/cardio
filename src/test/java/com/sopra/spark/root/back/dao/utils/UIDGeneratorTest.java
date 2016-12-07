package com.sopra.spark.root.back.dao.utils;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.sopra.agile.cardio.dao.utils.UIDGenerator;

public class UIDGeneratorTest {

	@Test
	public void testGetUniqueIdPrefix() {
		String uid = UIDGenerator.getUniqueId("TST");
		assertThat("UID should start with prefix TST-", uid, startsWith("TST-"));
	}

	@Test
	public void testGetUniqueId() {
		int nbItems = 1000;

		// Generate UIDs
		List<String> uniqueIds = new ArrayList<String>();
		for (int i = 0; i < nbItems; i++) {
			uniqueIds.add(UIDGenerator.getUniqueId("TST"));
		}

		// Remove duplicates
		List<String> distinctIds = uniqueIds.stream().sorted().distinct().collect(Collectors.toList());

		assertEquals("UIDs must be unique", nbItems, distinctIds.size());
	}
}
