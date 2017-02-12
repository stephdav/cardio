package com.sopra.agile.cardio.back.dao;

import static com.sopra.agile.cardio.back.dao.DatabaseUtils.count;
import static com.sopra.agile.cardio.back.dao.DatabaseUtils.initDbConnexion;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Activity;
import com.sopra.agile.cardio.common.model.ActivityStatus;
import com.sopra.agile.cardio.common.model.ActivityType;

public class ActivityDaoTest {

    private static JdbcTemplate jdbc;

    private ActivityDao dao;

    private static final String ACTIVITIES = "ACTIVITIES";

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("insert-dataTest.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new ActivityDaoImpl(jdbc);
    }

    @Test
    public void testConstructor() {
        assertNotNull(dao);
    }

    @Test
    public void testAll() throws CardioTechnicalException {
        List<Activity> users = dao.all();
        assertNotNull(users);
        assertEquals(count(jdbc, ACTIVITIES), users.size());
    }

    @Test
    public void testFindActivity() throws CardioTechnicalException {

        // Activity must be found
        Activity activity = dao.find("ACT-03");
        assertNotNull(activity);
        assertEquals(ActivityType.US, activity.getType());
        assertEquals("3", activity.getName());
        assertEquals("activity 3", activity.getDescription());
        assertEquals(ActivityStatus.PENDING, activity.getStatus());

        // Activity not found
        Activity activityUNK = dao.find("UNK");
        assertNull(activityUNK);
    }

    @Test
    public void testAddActivity() throws CardioTechnicalException {
        int count = count(jdbc, ACTIVITIES);
        Activity activity = new Activity(null, ActivityType.US, "TST", "TST", ActivityStatus.DRAFT);
        dao.add(activity);
        assertEquals(count + 1, count(jdbc, ACTIVITIES));
    }

    @Test
    public void testFindByName() throws CardioTechnicalException {
        // Activity must be found
        Activity activity = dao.findByName("3");
        assertNotNull(activity);
        assertEquals("ACT-03", activity.getId());

        // Activity not found
        Activity activityUNK = dao.findByName("UNK");
        assertNull(activityUNK);
    }

}
