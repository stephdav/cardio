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
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.utils.converter.Converter;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Sprint;

public class SprintDaoTest {

    private static JdbcTemplate jdbc;

    private SprintDao dao;

    private static final String SPRINTS = "SPRINTS";

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("insert-dataTest.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new SprintDaoImpl(jdbc);
        ReflectionTestUtils.setField(dao, "mapper", new Converter());
    }

    @Test
    public void testConstructor() {
        assertNotNull(dao);
    }

    @Test
    public void testAll() throws CardioTechnicalException {
        List<Sprint> sprints = dao.all();
        assertNotNull(sprints);
        assertEquals(count(jdbc, SPRINTS), sprints.size());
    }

    @Test
    public void testFindSprint() throws CardioTechnicalException {
        // Sprint must be found
        Sprint sprint = dao.find("SPR-1");
        assertNotNull(sprint);
        assertEquals("1", sprint.getName());
        assertEquals("2016-01-01", sprint.getStartDate());
        assertEquals("2016-01-31", sprint.getEndDate());
        assertEquals("sprint1 goal", sprint.getGoal());
        assertEquals(13, sprint.getCommitment());
        assertEquals(11, sprint.getVelocity());

        // Sprint not found
        Sprint unk = dao.find("UNK");
        assertNull(unk);
    }

    @Test
    public void testAddSprint() throws CardioTechnicalException {
        int count = count(jdbc, SPRINTS);
        dao.add(new Sprint(null, "2016-03", "2016-03-01", "2016-03-31"));
        assertEquals(count + 1, count(jdbc, SPRINTS));
    }

    @Test
    public void testFindSprintByName() throws CardioTechnicalException {
        // Sprint must be found
        Sprint sprint = dao.findByName("0");
        assertNotNull(sprint);
        assertEquals("0", sprint.getName());

        // Sprint not found
        Sprint unk = dao.findByName("UNK");
        assertNull(unk);
    }

    @Test
    public void testUpdateSprint() throws CardioTechnicalException {
        int count = count(jdbc, SPRINTS);
        Sprint sprint = new Sprint("SPR-0", "0", "2015-12-01", "2015-12-31");
        sprint.setGoal("mise à jour");
        sprint.setCommitment(34);
        sprint.setVelocity(20);
        Sprint updated = dao.update(sprint);

        assertEquals(count, count(jdbc, SPRINTS));
        assertNotNull(updated);
        assertEquals("mise à jour", updated.getGoal());
        assertEquals(20, updated.getVelocity());
    }

    @Test
    public void testRemoveSprint() throws CardioTechnicalException {
        int count = count(jdbc, SPRINTS);
        dao.remove("TO_BE_DELETED");
        assertEquals(count - 1, count(jdbc, SPRINTS));
    }

    @Test
    public void testCurrentSprint() throws CardioTechnicalException {
        // User must be found
        Sprint sprint = dao.current();
        assertNotNull(sprint);
        assertEquals("SPR-2", sprint.getId());
        assertEquals("current", sprint.getName());
        assertEquals("sprint2 goal", sprint.getGoal());
        assertEquals(44, sprint.getCommitment());
        assertEquals(23, sprint.getVelocity());
    }

    @Test
    public void testAllCompleted() throws CardioTechnicalException {
        List<Sprint> sprints = dao.allCompleted();
        assertNotNull(sprints);
        assertEquals(count(jdbc, SPRINTS) - 1, sprints.size());
        // TODO : assert that sprints are sorted => impact on testAddSprint
    }

    @Test
    public void testFindSprintByDay() throws CardioTechnicalException {

        List<Sprint> sprints = dao.findByDay("2016-01-15");

        assertNotNull(sprints);
        assertEquals(1, sprints.size());

        Sprint sprint = sprints.get(0);
        assertEquals("1", sprint.getName());
    }

    @Test
    public void testFindSprintByDayNow() throws CardioTechnicalException {

        List<Sprint> sprints = dao.findByDay("now");

        assertNotNull(sprints);
        assertEquals(1, sprints.size());

        Sprint sprint = sprints.get(0);
        assertEquals("current", sprint.getName());
    }

    @Test
    public void testOverlaping() throws CardioTechnicalException {

        // 0 : 2015-12-01 > 2015-12-31
        // 1 : 2016-01-01 > 2016-01-31

        Sprint sprint = new Sprint("id", "name", "2015-01-01", "2015-02-01");

        // 2015-01-01 > 2015-02-01
        assertOverlaping(sprint, 0);

        // 2015-01-01 > 2015-12-01
        sprint.setEndDate("2015-12-01");
        assertOverlaping(sprint, 1);

        // 2015-01-01 > 2015-12-15
        sprint.setEndDate("2015-12-15");
        assertOverlaping(sprint, 1);

        // 2015-01-01 > 2016-01-01
        sprint.setEndDate("2016-01-01");
        assertOverlaping(sprint, 2);

        // 2015-12-15 > 2016-01-01
        sprint.setStartDate("2015-12-15");
        assertOverlaping(sprint, 2);

        // 2015-12-15 > 2016-01-31
        sprint.setEndDate("2016-01-31");
        assertOverlaping(sprint, 2);

        // 2015-12-15 > 2016-02-15
        sprint.setEndDate("2016-02-15");
        assertOverlaping(sprint, 2);

        // 2016-02-01 > 2016-02-15
        sprint.setStartDate("2016-02-01");
        assertOverlaping(sprint, 0);
    }

    private void assertOverlaping(Sprint sprint, int expected) throws CardioTechnicalException {
        List<Sprint> sprints = dao.overlaping(sprint);
        assertNotNull(sprints);
        assertEquals(expected, sprints.size());
    }
}
